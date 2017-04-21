package com.kaffatech.latte.scheduling.tracker.model;

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
     * Job注册者名
     */
    private String registratorName;

    /**
     * Job表达式
     */
    private String expression;

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

    public String getRegistratorName() {
        return registratorName;
    }

    public void setRegistratorName(String registratorName) {
        this.registratorName = registratorName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
