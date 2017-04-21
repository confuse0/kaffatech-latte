package com.kaffatech.latte.ctx.cc.model;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.ctx.cc.model.type.ServerStatus;

import java.util.Date;

/**
 * @author lingzhen on 16/11/18.
 */
public class ServerSnapshot extends IdBean {

    /**
     * 版本序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 服务器集群名称
     */
    private String clusterName;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 健康状况
     */
    private String health;

    /**
     * 服务器状况
     */
    private ServerStatus status;

    /**
     * 集群版本号
     */
    private Long clusterVer;

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

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }

    public Long getClusterVer() {
        return clusterVer;
    }

    public void setClusterVer(Long clusterVer) {
        this.clusterVer = clusterVer;
    }
}
