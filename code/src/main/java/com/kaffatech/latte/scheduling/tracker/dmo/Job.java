package com.kaffatech.latte.scheduling.tracker.dmo;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.scheduling.dmo.type.JobStatus;

/**
 * @author lingzhen on 16/11/15.
 */
public class Job extends IdBean {

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
     * Job执行者信息
     */
    private String runner;

    /**
     * Job表达式
     */
    private String cronExpression;

    /**
     * Job状态
     */
    private JobStatus status;

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

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
