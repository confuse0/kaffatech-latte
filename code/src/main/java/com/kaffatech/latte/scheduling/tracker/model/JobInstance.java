package com.kaffatech.latte.scheduling.tracker.model;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.scheduling.dmo.type.JobInstanceStatus;

import java.util.Date;

/**
 * @author lingzhen on 16/11/15.
 */
public class JobInstance extends IdBean {

    private static final long serialVersionUID = 1L;

    /**
     * Job
     */
    private Job job;

    /**
     * 任务执行分片
     */
    private String shardingGroup;

    /**
     * 第一次执行时间
     */
    private Date firstExecuteTime;

    /**
     * 上一次执行时间
     */
    private Date lastExecuteTime;

    /**
     * 执行次数
     */
    private Integer executeTimes;

    /**
     * 执行状态
     */
    private JobInstanceStatus status;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getShardingGroup() {
        return shardingGroup;
    }

    public void setShardingGroup(String shardingGroup) {
        this.shardingGroup = shardingGroup;
    }

    public Date getFirstExecuteTime() {
        return firstExecuteTime;
    }

    public void setFirstExecuteTime(Date firstExecuteTime) {
        this.firstExecuteTime = firstExecuteTime;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Integer getExecuteTimes() {
        return executeTimes;
    }

    public void setExecuteTimes(Integer executeTimes) {
        this.executeTimes = executeTimes;
    }

    public JobInstanceStatus getStatus() {
        return status;
    }

    public void setStatus(JobInstanceStatus status) {
        this.status = status;
    }
}
