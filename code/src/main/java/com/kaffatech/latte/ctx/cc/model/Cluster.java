package com.kaffatech.latte.ctx.cc.model;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.ctx.cc.model.type.ClusterStatus;
import com.kaffatech.latte.ctx.cc.model.type.ServerStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lingzhen on 16/11/21.
 */
public class Cluster extends IdBean {

    /**
     * 版本序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 健康
     */
    public static final String HEALTH = "H";


    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 主服务器名
     */
    private String masterName;

    /**
     * 分配时间
     */
    private Date lastAllocateTime;

    /**
     * 服务器状况
     */
    private ClusterStatus status;

    /**
     * 服务器列表版本号
     */
    private Long clusterVer;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Date getLastAllocateTime() {
        return lastAllocateTime;
    }

    public void setLastAllocateTime(Date lastAllocateTime) {
        this.lastAllocateTime = lastAllocateTime;
    }

    public ClusterStatus getStatus() {
        return status;
    }

    public void setStatus(ClusterStatus status) {
        this.status = status;
    }

    public Long getClusterVer() {
        return clusterVer;
    }

    public void setClusterVer(Long clusterVer) {
        this.clusterVer = clusterVer;
    }
}
