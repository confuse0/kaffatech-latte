package com.kaffatech.latte.ctx.cc.model;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.ctx.cc.model.type.ServerStatus;
import com.kaffatech.latte.commons.bean.model.type.BooleanType;

import java.util.Date;

/**
 * @author lingzhen on 16/11/18.
 */
public class Server extends IdBean {

    /**
     * 版本序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器集群名称
     */
    private String clusterName;

    /**
     * 上一次访问时间
     */
    private Date lastAccessTime;

    /**
     * 健康状况
     */
    private String health;

    /**
     * 服务器状况
     */
    private ServerStatus status;

    /**
     * 版本号
     */
    private Long ver;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
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

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }
}
