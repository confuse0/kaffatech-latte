package com.kaffatech.latte.ctx.id.model;

import com.kaffatech.latte.commons.bean.model.IdBean;

/**
 * @author lingzhen on 16/11/24.
 */
public class ShardingId extends IdBean {

    /**
     * 业务集群名称
     */
    private String clusterName;

    /**
     * ShardingId的服务器名称
     */
    private String serverName;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
