package com.kaffatech.latte.ctx.cc.impl;

import com.kaffatech.latte.ctx.cc.ClusterManager;
import com.kaffatech.latte.ctx.cc.ConfigCenterManager;
import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Server;

import java.util.List;

/**
 * @author lingzhen on 16/11/22.
 */
public class ClusterManagerImpl implements ClusterManager {

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * 配置中心管理器
     */
    private ConfigCenterManager configCenterManager;

    @Override
    public void heartbeat(String name, String health) {
    }

    @Override
    public Cluster queryCluster() {
        return configCenterManager.queryCluster(clusterName);
    }

    @Override
    public Server queryServer(String serverName) {
        return configCenterManager.queryServer(clusterName, serverName);
    }

    @Override
    public List<Server> queryServerList() {
        return configCenterManager.queryServerList(clusterName);
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setConfigCenterManager(ConfigCenterManager configCenterManager) {
        this.configCenterManager = configCenterManager;
    }
}
