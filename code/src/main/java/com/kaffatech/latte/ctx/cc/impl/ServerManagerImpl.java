package com.kaffatech.latte.ctx.cc.impl;

import com.kaffatech.latte.commons.bean.util.TypeBeanUtils;
import com.kaffatech.latte.ctx.cc.ConfigCenterManager;
import com.kaffatech.latte.ctx.cc.ServerManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Constants;
import com.kaffatech.latte.ctx.cc.model.Server;
import com.kaffatech.latte.ctx.cc.model.type.ServerStatus;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.commons.bean.model.type.BooleanType;
import com.kaffatech.latte.ctx.id.IdGenerator;
import com.kaffatech.latte.commons.json.util.JsonUtils;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lingzhen on 16/11/22.
 */
public class ServerManagerImpl implements ServerManager {

    @Resource
    private DbAccessor dbAccessor;

    @Resource
    private ConfigCenterManager configCenterManager;

    @Resource
    private IdGenerator idGenerator;

    private static final ClusterRowMapper CLUSTER_ROW_MAPPER = new ClusterRowMapper();

    private static final ServerRowMapper SERVER_ROW_MAPPER = new ServerRowMapper();

    private static final String QUERY_ALL_CLUSTER_SQL ="SELECT * FROM CLUSTER";

    private static final String QUERY_CLUSTER_SQL ="SELECT * FROM CLUSTER WHERE NAME = ?";

    private static final String UPDATE_CLUSTER_SQL ="UPDATE CLUSTER SET VER = VER + 1, MASTER = ? WHERE NAME = ? AND VER = ?";

    private static final String QUERY_SERVER_LIST_SQL ="SELECT * FROM SERVER WHERE CLUSTER = ? AND VER = ?";

    private static final String UPDATE_SERVER_SQL ="UPDATE SERVER SET LAST_ACCESS_TIME = ?, HEALTH = ? WHERE CLUSTER = ? AND NAME = ? AND VER = ?";

    private static final String INSERT_SERVER_SQL ="INSERT INTO SERVER (ID, NAME, CLUSTER, IS_MASTER, LAST_ACCESS_TIME, HEALTH, STATUS, VER, CREATE_TIME, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void heartbeat(String name, String clusterName, String health) {
        // 查询集群信息
        Cluster cluster = dbAccessor.queryForObject(QUERY_CLUSTER_SQL, new Object[]{clusterName}, CLUSTER_ROW_MAPPER);

        // 尝试更新心跳时间
        Date now = new Date();
        int rowNo = dbAccessor.update(UPDATE_SERVER_SQL, now, health, clusterName, name, cluster.getVer());
        if (rowNo <= 0) {
            // 没有该服务器,需要插入一条
            dbAccessor.update(INSERT_SERVER_SQL, idGenerator.next(), name, clusterName, BooleanType.NO.getCode(), now, health, ServerStatus.RUNNING, null, now, now);
        }
    }

    @Override
    public Cluster getCluster(String clusterName) {
        // 查询集群信息
        Cluster cluster = dbAccessor.queryForObject(QUERY_CLUSTER_SQL, new Object[]{clusterName}, CLUSTER_ROW_MAPPER);

        List<Server> serverList = dbAccessor.query(QUERY_SERVER_LIST_SQL, new Object[]{clusterName, cluster.getVer()}, SERVER_ROW_MAPPER);

        cluster.setServerList(serverList);

        return cluster;
    }

    /**
     * 监控所有集群
     */
    private void monitor() {
        if (isMaster()) {
            // 如果是主服务器，则需要负责监控各集群情况
            List<Cluster> clusterList = dbAccessor.query(QUERY_ALL_CLUSTER_SQL, CLUSTER_ROW_MAPPER);

            for (Cluster cluster : clusterList) {
                monitorOneCluster(cluster);
            }
        }
    }

    /**
     * 监控一个集群的
     *
     * @param cluster
     */
    private void monitorOneCluster(Cluster cluster) {
        List<Server> serverList = dbAccessor.query(QUERY_SERVER_LIST_SQL, new Object[] {cluster.getName(), cluster.getVer()}, SERVER_ROW_MAPPER);

        boolean needToUpgrade = false;
        for (Server server : serverList) {
            if (server.getVer() == null) {
                // 新机器加入
                server.setVer(cluster.getVer() + 1L);
                needToUpgrade = true;
            }

            if (DateUtils.addSeconds(server.getLastAccessTime(), Constants.SERVER_MAX_HEARTBEAT).compareTo(new Date()) < 0) {
                // 可能该服务器已经当机
                server.setVer(cluster.getVer() + 1L);
                server.setStatus(ServerStatus.STOPED);
                needToUpgrade = true;
            } else if (!ServerStatus.RUNNING.equals(server.getStatus())) {
                // 服务器已经活跃
                server.setVer(cluster.getVer() + 1L);
                server.setStatus(ServerStatus.RUNNING);
                needToUpgrade = true;
            }
        }

        if (needToUpgrade) {
            // 更新集群信息
            cluster.setVer(cluster.getVer() + 1L);
            cluster.setLastAllocateTime(new Date());
            cluster.setServerList(serverList);

            upgrade(cluster);
        }

    }

    /**
     * 更新集群版本信息(需要在一个事务里)
     *
     * @param cluster
     */
    private void upgrade(Cluster cluster) {
        boolean existMaster = false;
        for (Server server : cluster.getServerList()) {
            if (!existMaster && ServerStatus.RUNNING.equals(server.getStatus())) {
                server.setIsMaster(BooleanType.YES);
                cluster.setMaster(server.getName());
                existMaster = true;
            } else {
                server.setIsMaster(BooleanType.NO);
            }
        }

        int rowNo = dbAccessor.update(UPDATE_CLUSTER_SQL, cluster.getMaster(), cluster.getName(), cluster.getVer());
        if (rowNo > 0) {
            // 更新集群信息成功
            for (Server server : cluster.getServerList()) {
                // 插入新的服务器信息
                Date now = new Date();
                dbAccessor.update(INSERT_SERVER_SQL, server.getId(), server.getName(), server.getCluster(), server.getIsMaster().getCode(), server.getLastAccessTime(), server.getHealth(), server.getStatus().getCode(), server.getVer(), now, now);
            }
        } else {
            // 存在并发更新的情况,不做处理
        }
    }

    /**
     * 判断自己是不是主
     * @return
     */
    private boolean isMaster() {
        String master = configCenterManager.takeMaster();
        return StringUtils.equals(master, IpAddressUtils.getHostAddress());
    }

    private static class ClusterRowMapper implements RowMapper<Cluster> {

        @Override
        public Cluster mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cluster cluster = new Cluster();
            cluster.setId(rs.getLong("ID"));
            cluster.setLastAllocateTime(rs.getDate("LAST_ALLOCATE_TIME"));
            cluster.setData(JsonUtils.toJsonObject(rs.getString("DATA"), Map.class));
            cluster.setVer(rs.getLong("VER"));
            return cluster;
        }
    }

    private static class ServerRowMapper implements RowMapper<Server> {

        @Override
        public Server mapRow(ResultSet rs, int rowNum) throws SQLException {
            Server server = new Server();
            server.setId(rs.getLong("ID"));
            server.setName(rs.getString("NAME"));
            server.setCluster(rs.getString("CLUSTER"));
            server.setIsMaster((BooleanType) TypeBeanUtils.getType(BooleanType.class, rs.getString("IS_MASTER")));
            server.setLastAccessTime(rs.getDate("LAST_ACCESS_TIME"));
            server.setHealth(rs.getString("HEALTH"));
            server.setStatus((ServerStatus) TypeBeanUtils.getType(ServerStatus.class, rs.getString("STATUS")));
            server.setVer(rs.getLong("VER"));
            return server;
        }
    }

}
