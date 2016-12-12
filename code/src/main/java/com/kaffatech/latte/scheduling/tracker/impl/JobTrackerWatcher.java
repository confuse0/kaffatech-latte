package com.kaffatech.latte.scheduling.tracker.impl;

import com.kaffatech.latte.commons.toolkit.base.ArrayUtils;
import com.kaffatech.latte.ctx.cc.ServerManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Server;
import com.kaffatech.latte.ctx.cc.model.type.ServerStatus;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.ctx.id.IdGenerator;
import com.kaffatech.latte.scheduling.SerializationJob;
import com.kaffatech.latte.scheduling.tracker.JobTrackerManager;
import com.kaffatech.latte.scheduling.tracker.dmo.JobTracker;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTrackerWatcher extends SerializationJob implements JobTrackerManager {

    @Resource
    private DbAccessor dbAccessor;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private ServerManager serverManager;

    private static final String CLUSTER_NAME = "jobTracker";

    private static final JobTrackerRowMapper JOB_TRACKER_ROW_MAPPER = new JobTrackerRowMapper();

    private static final String QUERY_JOB_TRACKER_SQL = "SELECT * FROM JOB_TRACKER WHERE SHARDING_VER = ?";

    private static final String INSERT_JOB_TRACKER_SQL = "INSERT INTO JOB_TRACKER (ID, NAME, SHARDING_LIST, SHARDING_VER, CREATE_TIME, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public String allocateShardingId(String name) {
        return null;
    }

    /**
     * 定时拉取集群数据,分配分片信息(未来改成watcher通知模式)
     */
    @Override
    public void process() {
        // 获取集群信息
        Cluster cluster = serverManager.getCluster(CLUSTER_NAME);
        if (StringUtils.equals(cluster.getMaster(), IpAddressUtils.getHostAddress())) {
            // 主服务器负责更新分片信息
            List<JobTracker> jobTrackerList = getJobTrackerList(cluster);
            if (!CollectionUtils.isEmpty(cluster.getServerList()) && CollectionUtils.isEmpty(jobTrackerList)) {
                // 如果对应版本没有数据则需要更新
                upgrade(cluster);
            }
        }
    }

    private List<JobTracker> getJobTrackerList(Cluster cluster) {
        return dbAccessor.query(QUERY_JOB_TRACKER_SQL, new Object[]{cluster.getVer()}, JOB_TRACKER_ROW_MAPPER);
    }

    private void upgrade(Cluster cluster) {
        List<JobTracker> jobTrackerList = allocateJobTrackerList(cluster);

        for (JobTracker jobTracker : jobTrackerList) {
            Date now = new Date();

            StringBuilder shardingSb = new StringBuilder();
            for (String each : jobTracker.getShardingGroup()) {
                shardingSb.append(each);
                shardingSb.append(",");
            }
            shardingSb.deleteCharAt(shardingSb.length() - 1);

            dbAccessor.update(INSERT_JOB_TRACKER_SQL, jobTracker.getId(), jobTracker.getName(), shardingSb.toString(), jobTracker.getShardingVer(), now, now);
        }
    }

    private List<JobTracker> allocateJobTrackerList(Cluster cluster) {
        List<Server> serverList = new ArrayList<Server>();

        for (Server server : cluster.getServerList()) {
            if (ServerStatus.RUNNING.equals(server.getStatus())) {
                serverList.add(server);
            }
        }

        List<JobTracker> jobTrackerList = new ArrayList<JobTracker>();
        for (int i = 0; i < serverList.size(); i++) {
            JobTracker jobTracker = new JobTracker();
            jobTracker.setId(idGenerator.next());
            jobTracker.setName(IpAddressUtils.getHostAddress());
            jobTracker.setShardingVer(cluster.getVer());
            List<String> shardingGroup = new ArrayList<String>();
            shardingGroup.add(String.valueOf(i));
            jobTracker.setShardingGroup(shardingGroup);

            jobTrackerList.add(jobTracker);
        }

        int idx = 0;
        for (int i = serverList.size(); i < cluster.getServerList().size(); i++) {
            // 差值重新分配
            jobTrackerList.get(idx).getShardingGroup().add(String.valueOf(i));
            idx++;
            if (idx >= jobTrackerList.size()) {
                idx = 0;
            }
        }

        return jobTrackerList;
    }

    private static class JobTrackerRowMapper implements RowMapper<JobTracker> {

        @Override
        public JobTracker mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobTracker jobTracker = new JobTracker();
            jobTracker.setId(rs.getLong("ID"));
            jobTracker.setName(rs.getString("NAME"));
            jobTracker.setShardingVer(rs.getLong("SHARDING_VER"));
            String shardingGroup = rs.getString("SHARDING_GROUP");
            if (StringUtils.isEmpty(shardingGroup)) {
                jobTracker.setShardingGroup(new ArrayList<String>());
            } else {
                jobTracker.setShardingGroup(ArrayUtils.asList(shardingGroup.split(",")));
            }

            return jobTracker;
        }
    }
}
