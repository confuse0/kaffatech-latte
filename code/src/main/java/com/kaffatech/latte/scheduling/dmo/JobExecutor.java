package com.kaffatech.latte.scheduling.dmo;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.scheduling.dmo.type.JobExecutorStatus;

/**
 * @author lingzhen on 16/11/15.
 */
public class JobExecutor extends IdBean {

    private static final long serialVersionUID = 1L;

    /**
     * Job分组
     */
    private String group;

    /**
     * Job执行者
     */
    private String executor;

    /**
     * Job执行者状态
     */
    private JobExecutorStatus status;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public JobExecutorStatus getStatus() {
        return status;
    }

    public void setStatus(JobExecutorStatus status) {
        this.status = status;
    }
}
