package com.kaffatech.latte.scheduling.tracker.dmo;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.scheduling.dmo.type.JobInstanceStatus;

import java.util.Date;
import java.util.Map;

/**
 * @author lingzhen on 16/11/15.
 */
public class JobInstance extends IdBean {

    private static final long serialVersionUID = 1L;

    /**
     * Job名称
     */
    private String name;

    /**
     * Job分组
     */
    private String group;

    /**
     * 任务数据
     */
    private Map<?, ?> data;

    /**
     * 分片
     */
    private String shardingId;

    /**
     * 最少的可执行时间
     */
    private Date minRunutableTime;

    /**
     * 第一次执行时间
     */
    private Date firstRunTime;

    /**
     * 上一次执行时间
     */
    private Date lastRunTime;

    /**
     * 执行状态
     */
    private JobInstanceStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<?, ?> getData() {
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }

    public String getShardingId() {
        return shardingId;
    }

    public void setShardingId(String shardingId) {
        this.shardingId = shardingId;
    }

    public Date getMinRunutableTime() {
        return minRunutableTime;
    }

    public void setMinRunutableTime(Date minRunutableTime) {
        this.minRunutableTime = minRunutableTime;
    }

    public Date getFirstRunTime() {
        return firstRunTime;
    }

    public void setFirstRunTime(Date firstRunTime) {
        this.firstRunTime = firstRunTime;
    }

    public Date getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Date lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public JobInstanceStatus getStatus() {
        return status;
    }

    public void setStatus(JobInstanceStatus status) {
        this.status = status;
    }
}
