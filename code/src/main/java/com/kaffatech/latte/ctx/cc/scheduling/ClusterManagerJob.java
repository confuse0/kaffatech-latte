package com.kaffatech.latte.ctx.cc.scheduling;

import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.ctx.cc.ClusterManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.scheduling.SerializationJob;

/**
 * @author lingzhen on 17/4/11.
 */
public class ClusterManagerJob extends SerializationJob {

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * 服务器管理器
     */
    private ClusterManager clusterManager;

    @Override
    protected void process() {
        // 发送心跳
        clusterManager.heartbeat(clusterName, IpAddressUtils.getHostAddress(), Cluster.HEALTH);
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

}
