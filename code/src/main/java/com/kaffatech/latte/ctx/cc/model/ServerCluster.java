package com.kaffatech.latte.ctx.cc.model;

import java.util.List;

/**
 * @author lingzhen on 16/11/21.
 */
public class ServerCluster extends Cluster {

    /**
     * 版本序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 集群服务器快照列表
     */
    List<ServerSnapshot> serverSnapshotList;

    public List<ServerSnapshot> getServerSnapshotList() {
        return serverSnapshotList;
    }

    public void setServerSnapshotList(List<ServerSnapshot> serverSnapshotList) {
        this.serverSnapshotList = serverSnapshotList;
    }
}
