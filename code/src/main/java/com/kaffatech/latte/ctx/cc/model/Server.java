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
    private String name;

    /**
     * 服务器组
     */
    private String cluster;

    /**
     * 是不是主
     */
    private BooleanType isMaster;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public BooleanType getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(BooleanType isMaster) {
        this.isMaster = isMaster;
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
