package com.kaffatech.latte.ctx.cc;

import com.kaffatech.latte.ctx.cc.model.Cluster;

/**
 * @author lingzhen on 16/11/18.
 */
public interface ServerManager {

    void heartbeat(String name, String clusterName, String health);

    Cluster getCluster(String clusterName);

}

