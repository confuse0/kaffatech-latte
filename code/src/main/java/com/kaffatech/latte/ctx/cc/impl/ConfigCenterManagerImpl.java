package com.kaffatech.latte.ctx.cc.impl;

import com.kaffatech.latte.commons.bean.model.type.BooleanType;
import com.kaffatech.latte.commons.bean.util.TypeBeanUtils;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.ctx.cc.ConfigCenterManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Server;
import com.kaffatech.latte.ctx.cc.model.ServerSnapshot;
import com.kaffatech.latte.ctx.cc.model.type.ServerStatus;
import com.kaffatech.latte.db.accessor.DbAccessor;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lingzhen on 16/11/22.
 */
public class ConfigCenterManagerImpl implements ConfigCenterManager {

    /**
     * 配置中心集群名
     */
    public static final String CONFIG_SERVER_CLUSTER_NAME = "CONFIG_CENTER";

    /**
     * 最大心跳间隔
     */
    public static final long SERVER_MAX_HEARTBEAT = 1L * 60L * 1000L;

    @Resource
    private DbAccessor dbAccessor;

    @Override
    public void heartbeat(String clusterName, String name, String health) {
        doHeartbeat(clusterName, name, health);
    }

    @Override
    public Cluster queryCluster(String clusterName) {
        return doQueryCluster(clusterName);
    }

    @Override
    public List<ServerSnapshot> queryServerSnapshotList(String clusterName, long clusterVer) {
        return doQueryServerSnapshotList(clusterName, clusterVer);
    }

    /**
     * 监控配置中心自己的集群
     */
    @Override
    public void monitorConfigCenter() {
        // 先发送配置中心服务器心跳
        doHeartbeat(CONFIG_SERVER_CLUSTER_NAME, IpAddressUtils.getHostAddress(), Cluster.HEALTH);

        // 判断配置中心主服务器是否健康
        Cluster configCenterCluster = doQueryCluster(CONFIG_SERVER_CLUSTER_NAME);
        Server configCenterMaster = doQueryServer(CONFIG_SERVER_CLUSTER_NAME, configCenterCluster.getMasterName());

        if (needToElect(configCenterMaster)) {
            // 需要重新选自己为主
            int rowNo = electMyself(configCenterCluster);
            if (rowNo > 0) {
                // 自己竞选成功，同时更新配置中心服务器集群
                newServerSnapshotList(configCenterCluster);
            } else {
                // 自己竞选失败就放弃吧，存在并发竞选可能
            }
        } else if (StringUtils.equals(IpAddressUtils.getHostAddress(), configCenterMaster.getServerName())) {
            // 自己原来就是主
            refreshServerSnapshotList(configCenterCluster);
        }
    }

    /**
     * 监控除自己外的其他集群
     */
    @Override
    public void monitorOthers() {
        // 配置中心主负责监控
        if (isConfigCenterMaster()) {
            // 查询所有集群
            List<Cluster> clusterList = queryAllCluster();
            for (Cluster cluster : clusterList) {
                if (!StringUtils.equals(CONFIG_SERVER_CLUSTER_NAME, cluster.getClusterName())) {
                    // 不是配置中心自己
                    monitorOther(cluster);
                }
            }
        }
    }

    /**
     * 监控其他某个集群
     *
     * @param cluster
     */
    private void monitorOther(Cluster cluster) {
        // 是否需要更新集群
        boolean upgradeCluster = false;

        // 查询主服务器信息
        Server master = doQueryServer(CONFIG_SERVER_CLUSTER_NAME, cluster.getMasterName());

        if (needToElect(master)) {
            // 需要重新选一个作为主
            List<ServerSnapshot> serverSnapshotList = getNewServerSnapshotList(cluster);
            ServerSnapshot newMasterSnapshot = null;
            for (ServerSnapshot serverSnapshot : serverSnapshotList) {
                if (ServerStatus.IN_SERVICE.equals(serverSnapshot)) {
                    // 正常服务
                    newMasterSnapshot = serverSnapshot;
                    break;
                }
            }
            if (newMasterSnapshot != null) {
                // 至少有一个主，如果所有服务器都挂了，无主可选就放弃吧
                int rowNo = elect(cluster, newMasterSnapshot);
                if (rowNo > 0) {
                    // 选主成功，同时更新配置中心服务器集群
                    newServerSnapshotList(cluster);
                } else {
                    // 选主失败则放弃，小概率存在并发可能
                }
            }
        } else {
            // 不需要更新主
            refreshServerSnapshotList(cluster);
        }
    }

    private boolean needToElect(Server master) {
        boolean needToElect = false;
        if (master == null) {
            // 没有主服务器
            needToElect = true;
        } else if (DateUtils.now().getTime() - master.getLastAccessTime().getTime() > SERVER_MAX_HEARTBEAT) {
            // 有主服务器但是主服务器心跳超时了
            needToElect = true;
        }

        return needToElect;
    }

    private boolean isConfigCenterMaster() {
        Cluster configCenterCluster = queryCluster(CONFIG_SERVER_CLUSTER_NAME);
        if (StringUtils.equals(IpAddressUtils.getHostAddress(), configCenterCluster.getMasterName())) {
            // 如果是自己
            return true;
        }
        return false;
    }

    private Server doHeartbeat(String clusterName, String name, String health) {
        // 尝试更新心跳时间
        Date now = new Date();
        String sql = "update %s_SERVER set HEALTH = ?, LAST_ACCESS_TIME = ?, UPDATE_TIME = ? where SERVER_NAME = ?";
        int rowNo = dbAccessor.update(String.format(sql, StringUtils.upperCase(clusterName)), health, now, now, name);
        if (rowNo <= 0) {
            // 不存在该服务器记录，则需要插入一条新的
            long nextSequence = dbAccessor.nextSequence(String.format("SEQ_%s_SERVER", StringUtils.upperCase(clusterName)));

            String insertSql = "insert into %s_SERVER (ID, SERVER_NAME, CLUSTER_NAME, HEALTH, LAST_ACCESS_TIME, CREATE_TIME, UPDATE_TIME, DELETE_FLAG) values (?, ?, ?, ?, ?, ?, ?, ?)";
            dbAccessor.update(String.format(insertSql, StringUtils.upperCase(clusterName)), nextSequence, name, clusterName, health, now, now, now, BooleanType.NO.getCode());
        }
        String querySql = "select * from %s_SERVER where CLUSTER_NAME = ? and SERVER_NAME = ?";

        Server server = dbAccessor.queryForObject(String.format(querySql, StringUtils.upperCase(clusterName)), new Object[]{clusterName, name}, new ServerRowMapper());

        return server;
    }

    private Cluster doQueryCluster(String clusterName) {
        String clusterQuerySql = "select * from CLUSTER where CLUSTER_NAME = ?";
        Cluster cluster = dbAccessor.queryForObject(clusterQuerySql, new Object[]{clusterName}, new ClusterRowMapper());

        return cluster;
    }

    private Server doQueryServer(String clusterName, String serverName) {
        if (StringUtils.isEmpty(serverName)) {
            return null;
        }
        String serverQuerySql = "select * from %s_SERVER where CLUSTER_NAME = ? AND SERVER_NAME = ?";
        Server server = dbAccessor.queryForObject(String.format(serverQuerySql, StringUtils.upperCase(clusterName)), new Object[]{clusterName, serverName}, new ServerRowMapper());
        return server;
    }

    private List<Server> queryServerList(String clusterName) {
        String sql = "select * from %s_SERVER where CLUSTER_NAME = ?";
        List<Server> serverList = dbAccessor.query(String.format(sql, StringUtils.upperCase(clusterName)), new Object[]{clusterName, ServerStatus.IN_SERVICE.getCode()}, new ServerRowMapper());
        return serverList;
    }

    private List<ServerSnapshot> doQueryServerSnapshotList(String clusterName, long clusterVer) {
        String sql = "select * from %s_SERVER_SNAPSHOT where CLUSTER_NAME = ? AND CLUSTER_VER = ?";
        return dbAccessor.query(String.format(sql, StringUtils.upperCase(clusterName)), new Object[]{clusterName, clusterVer}, new ServerSnapshotRowMapper());
    }

    private void refreshServerSnapshotList(Cluster cluster) {
        List<ServerSnapshot> newServerSnapshotList = checkServerSnapshotList(cluster);
        if (newServerSnapshotList != null) {
            // 需要更新
            int rowNo = upgradeClusterVer(cluster);
            if (rowNo > 0) {
                // 集群信息更新成功，需要增加新版本服务器快照
                insertServerSnapshotList(newServerSnapshotList);
            }
        }
    }

    private List<ServerSnapshot> checkServerSnapshotList(Cluster cluster) {
        // 查询当前版本的服务器快照
        List<ServerSnapshot> serverSnapshotList = queryServerSnapshotList(cluster.getClusterName(), cluster.getClusterVer());
        // 查询最新的服务器快照
        List<ServerSnapshot> newServerSnapshotList = getNewServerSnapshotList(cluster);
        if (CollectionUtils.isEmpty(serverSnapshotList)) {
            // 如果还没有服务器快照信息直接返回最新的
            return newServerSnapshotList;
        } else {
            // 如果存在旧的需要进行比较
            if (newServerSnapshotList.size() != serverSnapshotList.size()) {
                // 数量都不一样直接返回最新的
                return newServerSnapshotList;
            } else {
                // 数量一样比较内容
                int i = 0;
                for (ServerSnapshot serverSnapshot : serverSnapshotList) {
                    ServerSnapshot newServerSnapshot = newServerSnapshotList.get(i);
                    if (!StringUtils.equals(serverSnapshot.getServerName(), newServerSnapshot.getServerName()) || StringUtils.equals(serverSnapshot.getHealth(), newServerSnapshot.getHealth()) || serverSnapshot.getStatus().equals(newServerSnapshot.getStatus())) {
                        // 如果服务器名字，服务器健康状况，服务器状态有一个不一样则不一样
                        return newServerSnapshotList;
                    }

                    i++;
                }
            }
        }
        return null;
    }

    private void newServerSnapshotList(Cluster cluster) {
        List<ServerSnapshot> serverSnapshotList = getNewServerSnapshotList(cluster);
        // 插入新版本的服务器快照
        insertServerSnapshotList(serverSnapshotList);
    }

    private List<ServerSnapshot> getNewServerSnapshotList(Cluster cluster) {
        List<Server> serverList = queryServerList(cluster.getClusterName());
        List<ServerSnapshot> serverSnapshotList = new ArrayList<ServerSnapshot>();
        for (Server server : serverList) {
            ServerSnapshot serverSnapshot = new ServerSnapshot();
            serverSnapshot.setId(dbAccessor.nextSequence("SEQ_%s_SERVER_SNAPSHOT"));
            serverSnapshot.setServerName(server.getServerName());
            serverSnapshot.setClusterName(server.getClusterName());
            serverSnapshot.setClusterVer(cluster.getClusterVer());
            serverSnapshot.setHealth(server.getHealth());
            Date now = DateUtils.now();
            if (now.getTime() - server.getLastAccessTime().getTime() > SERVER_MAX_HEARTBEAT) {
                // 超过最大心跳间隔,则认为挂机
                serverSnapshot.setStatus(ServerStatus.STOPED);
            } else {
                serverSnapshot.setStatus(ServerStatus.IN_SERVICE);
            }
            serverSnapshot.setCreateTime(now);
            serverSnapshot.setUpdateTime(serverSnapshot.getCreateTime());
            serverSnapshot.setDeleteFlag(BooleanType.NO);

            serverSnapshotList.add(serverSnapshot);
        }
        return serverSnapshotList;
    }

    private void insertServerSnapshotList(List<ServerSnapshot> serverSnapshotList) {
        String sql = "insert into () values ()";
        dbAccessor.update(sql);
    }

    private int electMyself(Cluster configCenterCluster) {
        Date now = new Date();
        String sql = "update CLUSTER set MASTER_NAME = ?, CLUSTER_VER = CLUSTER_VER + 1, LAST_ACCESS_TIME = ?, UPDATE_TIME = ? where CLUSTER_NAME = ? AND CLUSTER_VER = ?";
        int rowNo = dbAccessor.update(sql, IpAddressUtils.getHostAddress(), now, now, configCenterCluster.getClusterName(), configCenterCluster.getClusterVer());
        if (rowNo > 0) {
            // 选举成功，更新版本号
            configCenterCluster.setClusterVer(configCenterCluster.getClusterVer() + 1);
            configCenterCluster.setLastAllocateTime(now);
        }
        return rowNo;
    }

    private int elect(Cluster cluster, ServerSnapshot master) {
        Date now = new Date();
        String sql = "update CLUSTER set MASTER_NAME = ?, CLUSTER_VER = CLUSTER_VER + 1, LAST_ACCESS_TIME = ?, UPDATE_TIME = ? where CLUSTER_NAME = ? AND CLUSTER_VER = ?";
        int rowNo = dbAccessor.update(sql, master.getServerName(), now, now, cluster.getClusterName(), cluster.getClusterVer());
        if (rowNo > 0) {
            // 选举成功，更新版本号
            cluster.setMasterName(master.getServerName());
            cluster.setClusterVer(cluster.getClusterVer() + 1);
            cluster.setLastAllocateTime(now);
        }
        return rowNo;
    }

    private int upgradeClusterVer(Cluster cluster) {
        Date now = new Date();
        String sql = "update CLUSTER set CLUSTER_VER = VER + 1, LAST_ALLOCATE_TIME = ?, UPDATE_TIME = ? where CLUSTER_NAME = ? AND CLUSTER_VER = ?";
        int rowNo = dbAccessor.update(sql, now, now, cluster.getClusterName(), cluster.getClusterVer());
        if (rowNo > 0) {
            // 更新成功更新model
            cluster.setClusterVer(cluster.getClusterVer() + 1);
            cluster.setLastAllocateTime(now);
            cluster.setUpdateTime(now);
        }
        return rowNo;
    }

    private List<Cluster> queryAllCluster() {
        String querySql = "select * from CLUSTER";
        List<Cluster> clusterList = dbAccessor.query(querySql, new ClusterRowMapper());
        return clusterList;
    }


    private int updateServerStatus(String clusterName, String name, ServerStatus serverStatus) {
        Date now = new Date();
        String sql = "update %s_SERVER set SERVER_STATUS = ?, LAST_ACCESS_TIME = ?, UPDATE_TIME = ? where SERVER_NAME = ?";
        return dbAccessor.update(String.format(sql, StringUtils.upperCase(clusterName)), serverStatus.getCode(), now, now, name);
    }

    private static class ClusterRowMapper implements RowMapper<Cluster> {

        @Override
        public Cluster mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cluster cluster = new Cluster();
            cluster.setId(rs.getLong("ID"));
            cluster.setLastAllocateTime(rs.getDate("LAST_ALLOCATE_TIME"));
            cluster.setClusterVer(rs.getLong("CLUSTER_VER"));
            cluster.setMasterName(rs.getString("MASTER_NAME"));
            cluster.setCreateTime(rs.getDate("CREATE_TIME"));
            cluster.setUpdateTime(rs.getDate("UPDATE_TIME"));
            cluster.setDeleteFlag(TypeBeanUtils.getType(BooleanType.class, rs.getString("DELETE_FLAG")));

            return cluster;
        }
    }

    private static class ServerTrackerRowMapper implements RowMapper<ServerSnapshot> {

        @Override
        public ServerSnapshot mapRow(ResultSet rs, int rowNum) throws SQLException {
            ServerSnapshot server = new ServerSnapshot();
            server.setId(rs.getLong("ID"));
            server.setServerName(rs.getString("SERVER_NAME"));
            server.setClusterName(rs.getString("CLUSTER_NAME"));
            server.setHealth(rs.getString("H"));
            server.setStatus((ServerStatus) TypeBeanUtils.getType(ServerStatus.class, rs.getString("STATUS")));
            server.setClusterVer(rs.getLong("CLUSTER_VER"));
            server.setCreateTime(rs.getDate("CREATE_TIME"));
            server.setUpdateTime(rs.getDate("UPDATE_TIME"));
            server.setDeleteFlag(TypeBeanUtils.getType(BooleanType.class, rs.getString("DELETE_FLAG")));
            return server;
        }
    }

    private static class ServerRowMapper implements RowMapper<Server> {

        @Override
        public Server mapRow(ResultSet rs, int rowNum) throws SQLException {
            Server server = new Server();
            server.setId(rs.getLong("ID"));
            server.setServerName(rs.getString("SERVER_NAME"));
            server.setClusterName(rs.getString("CLUSTER_NAME"));
            server.setLastAccessTime(rs.getDate("LAST_ACCESS_TIME"));
            server.setHealth(rs.getString("H"));
            server.setCreateTime(rs.getDate("CREATE_TIME"));
            server.setUpdateTime(rs.getDate("UPDATE_TIME"));
            server.setDeleteFlag(TypeBeanUtils.getType(BooleanType.class, rs.getString("DELETE_FLAG")));
            return server;
        }
    }

    private static class ServerSnapshotRowMapper implements RowMapper<ServerSnapshot> {

        @Override
        public ServerSnapshot mapRow(ResultSet rs, int rowNum) throws SQLException {
            ServerSnapshot server = new ServerSnapshot();
            server.setId(rs.getLong("ID"));
            server.setServerName(rs.getString("SERVER_NAME"));
            server.setClusterName(rs.getString("CLUSTER_NAME"));
            server.setHealth(rs.getString("H"));
            server.setStatus(TypeBeanUtils.getType(ServerStatus.class, rs.getString("STATUS")));
            server.setCreateTime(rs.getDate("CREATE_TIME"));
            server.setUpdateTime(rs.getDate("UPDATE_TIME"));
            server.setDeleteFlag(TypeBeanUtils.getType(BooleanType.class, rs.getString("DELETE_FLAG")));
            return server;
        }
    }

}
