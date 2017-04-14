package com.kaffatech.latte.ctx.cc.impl;

import com.kaffatech.latte.commons.bean.model.type.BooleanType;
import com.kaffatech.latte.commons.bean.util.TypeBeanUtils;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.ctx.cc.ConfigCenterManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Constants;
import com.kaffatech.latte.ctx.cc.model.Server;
import com.kaffatech.latte.ctx.cc.model.type.ServerStatus;
import com.kaffatech.latte.db.accessor.DbAccessor;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author lingzhen on 16/11/22.
 */
public class ConfigCenterManagerImpl implements ConfigCenterManager {

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
    public List<Server> queryServerList(String clusterName) {
        return doQueryServerList(clusterName);
    }


    /**
     * 监控配置中心自己的集群
     */
    @Override
    public void monitorConfigCenter() {
        // 发送自己的心跳
        Server myself = doHeartbeat(Constants.CONFIG_SERVER_CLUSTER_NAME, IpAddressUtils.getHostAddress(), "H");

        // 判断配置中心主服务器是否健康
        Cluster configCenterCluster = doQueryCluster(Constants.CONFIG_SERVER_CLUSTER_NAME);
        Server configCenterMaster = configCenterCluster.getMaster();
        boolean needToElect = false;
        if (configCenterMaster == null) {
            // 没有主服务器
            needToElect = true;
        } else if (DateUtils.now().getTime() - configCenterMaster.getLastAccessTime().getTime() > Constants.SERVER_MAX_HEARTBEAT) {
            // 有主服务器但是主服务器心跳超时了
            needToElect = true;
        }
        if (needToElect) {
            // 需要重新选自己为主
            configCenterMaster = myself;
            configCenterCluster.setMaster(myself);
            updateClusterMasterAndVer(Constants.CONFIG_SERVER_CLUSTER_NAME, configCenterMaster.getId());
        }

        if (StringUtils.equals(IpAddressUtils.getHostAddress(), configCenterMaster.getServerName())) {
            // 自己就是主需要进行配置中心集群的控制工作
            monitorCluster(configCenterCluster);
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
                if (!StringUtils.equals(Constants.CONFIG_SERVER_CLUSTER_NAME, cluster.getClusterName())) {
                    // 不是配置中心自己
                    monitorCluster(cluster);
                }
            }
        }
    }

    /**
     * 监控集群
     *
     * @param cluster
     */
    private void monitorCluster(Cluster cluster) {
        // 是否需要更新集群
        boolean upgradeCluster = false;

        // 判断是否需要选举
        boolean needToElect = needToElect(cluster);

        Server newMaster = null;
        List<Server> serverList = null;
        for (Server each : serverList) {
            if (DateUtils.now().getTime() - each.getLastAccessTime().getTime() > Constants.SERVER_MAX_HEARTBEAT) {
                // 已经超时
                if (ServerStatus.IN_SERVICE.equals(each.getStatus())) {
                    // 服务中的服务器均转为停止
                    updateServerStatus(each.getClusterName(), each.getServerName(), ServerStatus.STOPED);

                    upgradeCluster = true;
                }
            } else {
                // 没有超时
                if (!ServerStatus.IN_SERVICE.equals(each.getStatus())) {
                    // 不是服务中的服务器均转为服务中
                    updateServerStatus(each.getClusterName(), each.getServerName(), ServerStatus.IN_SERVICE);

                    upgradeCluster = true;

                }
                if (needToElect && newMaster == null) {
                    // 需要选举并且没有选举出来的情况下选择第一个服务器作为主
                    newMaster = each;
                    upgradeCluster = true;
                }
            }
        }
        if (upgradeCluster) {
            // 更新集群
            if (newMaster == null) {
                // 不需要更新主
                updateClusterVer(cluster.getClusterName());
            } else {
                // 需要更新主
                updateClusterMasterAndVer(cluster.getClusterName(), newMaster.getId());
            }

        }
    }

    private boolean needToElect(Cluster cluster) {
        if (cluster.getMaster() == null) {
            // 没有主服务器需要选举
            return true;
        }

        if (DateUtils.now().getTime() - cluster.getMaster().getLastAccessTime().getTime() > Constants.SERVER_MAX_HEARTBEAT) {
            // 主服务器心跳超时,可能当机,需要选举
            return true;
        }

        return false;
    }

    private boolean isConfigCenterMaster() {
        Cluster configCenterCluster = queryCluster(Constants.CONFIG_SERVER_CLUSTER_NAME);
        if (StringUtils.equals(IpAddressUtils.getHostAddress(), configCenterCluster.getMaster().getServerName())) {
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
            // 不存在该服务器
            String insertSql = "insert into %s_SERVER (ID, SERVER_NAME, CLUSTER_NAME, HEALTH, LAST_ACCESS_TIME, STATUS, CREATE_TIME, UPDATE_TIME, DELETE_FLAG) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            dbAccessor.update(String.format(insertSql, StringUtils.upperCase(clusterName)), 1L, name, clusterName, health, now, ServerStatus.RUNNING.getCode(), now, now, BooleanType.NO.getCode());
        }
        String querySql = "select * from %s_SERVER where CLUSTER_NAME = ? and SERVER_NAME = ?";

        Server server = dbAccessor.queryForObject(String.format(querySql, StringUtils.upperCase(clusterName)), new Object[]{clusterName, name}, new ServerRowMapper());

        return server;
    }

    private Cluster doQueryCluster(String clusterName) {
        String clusterQuerySql = "select * from CLUSTER where NAME = ?";
        Cluster cluster = dbAccessor.queryForObject(clusterQuerySql, new Object[]{clusterName}, new ClusterRowMapper());

        String serverQuerySql = "select * from %s_SERVER where ID = ?";
        Server master = dbAccessor.queryForObject(String.format(serverQuerySql, StringUtils.upperCase(clusterName)), new Object[]{cluster.getMaster().getId()}, new ServerRowMapper());
        cluster.setMaster(master);
        return cluster;
    }

    private List<Server> doQueryServerList(String clusterName) {
        String serverQuerySql = "select * from %s_SERVER where CLUSTER_NAME = ? AND STATUS = ?";
        List<Server> serverList = dbAccessor.query(String.format(serverQuerySql, StringUtils.upperCase(clusterName)), new Object[]{clusterName, ServerStatus.IN_SERVICE.getCode()}, new ServerRowMapper());
        return serverList;
    }

    private int updateClusterMasterAndVer(String clusterName, long masterId) {
        Date now = new Date();
        String sql = "update CLUSTER set MASTER_ID = ?, VER = VER + 1, LAST_ACCESS_TIME = ?, UPDATE_TIME = ? where CLUSTER_NAME = ?";
        return dbAccessor.update(sql, masterId, now, now, clusterName);
    }

    private int updateClusterVer(String clusterName) {
        Date now = new Date();
        String sql = "update CLUSTER set VER = VER + 1, LAST_ACCESS_TIME = ?, UPDATE_TIME = ? where CLUSTER_NAME = ?";
        return dbAccessor.update(sql, now, now, clusterName);
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
            cluster.setVer(rs.getLong("VER"));
            Long masterId = rs.getLong("MASTER_ID");
            if (masterId != null) {
                Server server = new Server();
                server.setId(masterId);
                cluster.setMaster(server);
            }

            return cluster;
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
            server.setStatus((ServerStatus) TypeBeanUtils.getType(ServerStatus.class, rs.getString("STATUS")));
            server.setVer(rs.getLong("VER"));
            return server;
        }
    }

}
