package com.kaffatech.latte.ctx.cc;

import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.ServerSnapshot;

import java.util.List;

/**
 * @author lingzhen on 16/11/22.
 */
public interface ConfigCenterManager {

    void heartbeat(String clusterName, String name, String health);

    Cluster queryCluster(String clusterName);

    List<ServerSnapshot> queryServerSnapshotList(String clusterName, long clusterVer);

    void monitorConfigCenter();

    void monitorOthers();

}
