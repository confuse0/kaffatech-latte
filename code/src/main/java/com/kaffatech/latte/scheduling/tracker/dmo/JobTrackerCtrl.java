package com.kaffatech.latte.scheduling.tracker.dmo;

import com.kaffatech.latte.commons.bean.model.IdBean;

import java.util.Date;

/**
 * @author lingzhen on 16/11/15.
 */
public class JobTrackerCtrl extends IdBean {

    private static final long serialVersionUID = 1L;

    /**
     * JobTracker
     */
    private String master;

    /**
     * 分配分片时间
     */
    private Date lastAllocateTime;

    /**
     * 分片版本号
     */
    private Long shardingVer;

    /**
     * 版本号
     */
    private Long ver;

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public Date getLastAllocateTime() {
        return lastAllocateTime;
    }

    public void setLastAllocateTime(Date lastAllocateTime) {
        this.lastAllocateTime = lastAllocateTime;
    }

    public Long getShardingVer() {
        return shardingVer;
    }

    public void setShardingVer(Long shardingVer) {
        this.shardingVer = shardingVer;
    }

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }
}
