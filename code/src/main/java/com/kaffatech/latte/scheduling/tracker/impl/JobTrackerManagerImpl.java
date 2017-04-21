package com.kaffatech.latte.scheduling.tracker.impl;

import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.ctx.cc.ClusterManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.ServerSnapshot;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.scheduling.SerializationJob;
import com.kaffatech.latte.scheduling.tracker.JobTrackerManager;
import com.kaffatech.latte.scheduling.tracker.model.JobSharding;
import com.kaffatech.latte.scheduling.tracker.model.JobTrackerContext;
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
public class JobTrackerManagerImpl extends SerializationJob implements JobTrackerManager {

    private static final String CLUSTER_NAME = "SCHEDULING_CENTER";

    /**
     * 任务中心集群管理器
     */
    private ClusterManager clusterManager;

    @Resource
    private DbAccessor dbAccessor;

    /**
     * 缓存的集群信息
     */
    private Cluster cachedCluster;

    @Override
    public String allocateShardingId(String name) {
        return null;
    }

    @Override
    public void process() {
        // 获取集群信息
        Cluster cluster = clusterManager.queryCluster(CLUSTER_NAME);
        if (!StringUtils.equals(IpAddressUtils.getHostAddress(), cluster.getMasterName())) {
            // 开始分配任务执行分片
            monitor(cluster);
        }
        cachedCluster = cluster;
    }

    /**
     * 分配任务执行分片
     */
    public void monitor(Cluster cluster) {
        if (cachedCluster != null && cachedCluster.getClusterVer().equals(cluster.getClusterVer())) {
            // 集群没有变化不需要重新分配
            return;
        }


        // 升级版本号
        JobTrackerContext ctx = upgradeJobTrackerContext(cluster);
        if (ctx != null) {
            // 升级成功则查询所有服务器
            List<JobSharding> jobShardingList = allocate(ctx);
            upgradeJobSharding(jobShardingList);
        }
    }

    private List<JobSharding> allocate(JobTrackerContext ctx) {
        List<ServerSnapshot> serverSnapshotList = clusterManager.queryServerSnapshotList(CLUSTER_NAME, ctx.getClusterVer());
        if (CollectionUtils.isEmpty(serverSnapshotList)) {
            throw new IllegalStateException("no server!");
        }

        List<JobSharding> jobShardingList = new ArrayList<JobSharding>();
        int no = ctx.getMaxShardingNo() / serverSnapshotList.size();
        int moreNo = ctx.getMaxShardingNo() % serverSnapshotList.size();

        int i = 0;
        int idx = 0;

        for (ServerSnapshot serverSnapshot : serverSnapshotList) {
            int n = no;
            if (i < moreNo) {
                n = no + 1;
            }

            StringBuilder sb = new StringBuilder();
            for (int m = 0; m < n; m++) {
                sb.append(idx);
                sb.append(",");
                idx++;
            }
            sb.deleteCharAt(sb.length() - 1);

            JobSharding jobSharding = new JobSharding();
            jobSharding.setServerName(serverSnapshot.getServerName());
            jobSharding.setShardingGroup(sb.toString());
            jobSharding.setShardingVer(ctx.getShardingVer());
            jobShardingList.add(jobSharding);

            i++;
        }
        return jobShardingList;
    }

    private JobTrackerContext upgradeJobTrackerContext(Cluster cluster) {
        JobTrackerContext ctx = queryJobTrackerContext();
        String updateSql = "update JOB_TRACKER_CONTEXT set SHARDING_VER = SHARDING_VER + 1 AND LAST_ALLOCATE_TIME = ? AND UPDATE_TIME = ? where SHARDING_VER = ?";
        Date now = new Date();
        int rowNo = dbAccessor.update(updateSql, now, now, ctx.getShardingVer());
        if (rowNo > 0) {
            // 更新成功
            ctx.setLastAllocateTime(now);
            ctx.setShardingVer(ctx.getShardingVer() + 1L);
            return ctx;
        }
        return null;
    }

    private void upgradeJobSharding(List<JobSharding> jobShardingList) {
        for (JobSharding each : jobShardingList) {

        }
    }

    private JobTrackerContext queryJobTrackerContext() {
        String querySql = "select * from JOB_TRACKER_CONTEXT where ID = 1";
        return dbAccessor.queryForObject(querySql, new JobTrackerContextRowMapper());
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    private static class JobTrackerContextRowMapper implements RowMapper<JobTrackerContext> {

        @Override
        public JobTrackerContext mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobTrackerContext ctx = new JobTrackerContext();

            return ctx;
        }
    }

    private static class JobShardingRowMapper implements RowMapper<JobSharding> {

        @Override
        public JobSharding mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobSharding jobSharding = new JobSharding();
            jobSharding.setId(rs.getLong("ID"));
            jobSharding.setShardingVer(rs.getLong("SHARDING_VER"));
            jobSharding.setShardingGroup(rs.getString("SHARDING_GROUP"));

            return jobSharding;
        }
    }

}
