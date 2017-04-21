package com.kaffatech.latte.ctx.cc;

import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.ServerSnapshot;

import java.util.List;

/**
 * @author lingzhen on 16/11/18.
 */
public interface ClusterManager {

    /**
     * 发送心跳
     *
     * @param serverName
     * @param health
     */
    void heartbeat(String clusterName, String serverName, String health);

    /**
     * 查询集群信息
     *
     * @param clusterName
     * @return
     */
    Cluster queryCluster(String clusterName);

    /**
     * 查询在服务中的集群服务器列表
     *
     * @param clusterName
     * @param clusterVer
     * @return
     */
    List<ServerSnapshot> queryServerSnapshotList(String clusterName, long clusterVer);

}

