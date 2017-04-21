package com.kaffatech.latte.ctx.cc.impl;

import com.kaffatech.latte.ctx.cc.ClusterManager;
import com.kaffatech.latte.ctx.cc.ConfigCenterManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Server;
import com.kaffatech.latte.ctx.cc.model.ServerSnapshot;

import java.util.List;

/**
 * @author lingzhen on 16/11/22.
 */
public class ClusterManagerImpl implements ClusterManager {

    /**
     * 配置中心管理器
     */
    private ConfigCenterManager configCenterManager;

    @Override
    public void heartbeat(String clusterName, String serverName, String health) {
        configCenterManager.heartbeat(clusterName, serverName, health);
    }

    @Override
    public Cluster queryCluster(String clusterName) {
        return configCenterManager.queryCluster(clusterName);
    }

    @Override
    public List<ServerSnapshot> queryServerSnapshotList(String clusterName, long clusterVer) {
        return configCenterManager.queryServerSnapshotList(clusterName, clusterVer);
    }

    public void setConfigCenterManager(ConfigCenterManager configCenterManager) {
        this.configCenterManager = configCenterManager;
    }
}
