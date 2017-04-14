package com.kaffatech.latte.scheduling.tracker.impl;

import com.kaffatech.latte.ctx.cc.ClusterManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Server;
import com.kaffatech.latte.scheduling.SerializationJob;
import com.kaffatech.latte.scheduling.tracker.JobTrackerManager;
import com.kaffatech.latte.scheduling.tracker.dmo.JobTracker;
import com.kaffatech.latte.scheduling.tracker.dmo.JobTrackerSharding;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTrackerManagerImpl extends SerializationJob implements JobTrackerManager {

    @Resource
    private ClusterManager clusterManager;

    private JobTrackerSharding jobTrackerSharding;

    @Override
    public String allocateShardingId(String name) {
        return null;
    }

    @Override
    public void process() {
        // 获取集群信息
        Cluster cluster = clusterManager.queryCluster();
        if (jobTrackerSharding == null) {
            // 第一次同步
            reallocate();
        }
    }

    private void reallocate() {
        JobTrackerSharding temp = new JobTrackerSharding();
        Cluster cluster = clusterManager.queryCluster();
                temp.setVer(cluster.getVer());
        List<Server> clusterServerList =  clusterManager.queryServerList();
        Map<String, JobTracker> jobTrackerMap = new HashMap<String, JobTracker>();
        for (Server server : clusterServerList) {
            JobTracker tracker = new JobTracker();
            //tracker.set
            //jobTrackerMap.put()
        }


//        JobTrackerCtrl trackerCtrl = dbAccessor.queryForObject("SELECT * FROM JOB_TRACKER_CTRL FOR UPDATE", JobTrackerCtrl.class);
//
//        boolean needToUpdate = false;
//        if (trackerCtrl.getShardingVer() <= 0L || (jobTrackerSharding != null && jobTrackerSharding.getJobTrackerCtrl().getVer() >= trackerCtrl.getVer())) {
//            // 需要进行分片分配同步
//            List<Server> serverList = clusterManager.getServer(SERVER_GROUP);
//
//            List<JobTracker> aliveList = new ArrayList<JobTracker>();
//            for (Server each : serverList) {
//                if (ServerStatus.RUNNING.equals(each.getStatus())) {
//                    // 存活的
//                    JobTracker tracker = new JobTracker();
//                    tracker.setId(idGenerator.next());
//                    tracker.setTracker(each.getName());
//                    tracker.setShardingVer(1L);
//                    tracker.setStatus(ServerStatus.RUNNING);
//                }
//            }
//


            // int rowNo = dbAccessor.update("UPDATE JOB_TRACKER_CTRL SET LAST_ALLOCATE_TIME = ? AND TRACKER = ?", new Object()[] {new Date(), IpAddressUtils.getHostAddress(), JobTrackerStatus.RUNNING.getCode(), ""});


//        }


//        if (jobTrackerSharding.getJobTrackerCtrl().getShardingVer().equals(trackerCtrl.getShardingVer())) {
//
//        } else {
//            // 分片信息版本需要更新
//
//        }
//
//        List<JobTracker> trackerList;
    }
}
