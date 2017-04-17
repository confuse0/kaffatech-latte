package com.kaffatech.latte.ctx.cc;

import com.kaffatech.latte.ctx.cc.model.Cluster;
import com.kaffatech.latte.ctx.cc.model.Server;

import java.util.List;

/**
 * @author lingzhen on 16/11/18.
 */
public interface ClusterManager {

    /**
     * 发送心跳
     *
     * @param name
     * @param health
     */
    void heartbeat(String name, String health);

    /**
     * 查询集群信息
     *
     * @return
     */
    Cluster queryCluster();

    /**
     * 查询集群服务器列表
     *
     * @return
     */
    List<Server> queryServerList();

    /**
     * 查询服务器信息
     *
     * @param serverName
     * @return
     */
    Server queryServer(String serverName);

}

