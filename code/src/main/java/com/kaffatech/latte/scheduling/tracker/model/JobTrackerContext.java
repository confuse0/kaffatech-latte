package com.kaffatech.latte.scheduling.tracker.model;

import com.kaffatech.latte.commons.bean.model.IdBean;

import java.util.Date;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTrackerContext extends IdBean {

    private static final long serialVersionUID = 1L;

    /**
     * Job分片数量
     */
    private Integer maxShardingNo;

    /**
     * 最近分配的时间
     */
    private Date lastAllocateTime;

    /**
     * 分片版本
     */
    private Long shardingVer;

    /**
     * 集群版本号
     */
    private Long clusterVer;

    public Integer getMaxShardingNo() {
        return maxShardingNo;
    }

    public void setMaxShardingNo(Integer maxShardingNo) {
        this.maxShardingNo = maxShardingNo;
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

    public Long getClusterVer() {
        return clusterVer;
    }

    public void setClusterVer(Long clusterVer) {
        this.clusterVer = clusterVer;
    }
}
