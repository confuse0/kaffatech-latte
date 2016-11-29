package com.kaffatech.latte.scheduling;

import com.kaffatech.latte.scheduling.dmo.JobExecutor;

import java.util.List;

/**
 * @author lingzhen on 16/11/15.
 */
public interface JobExecutorManager {

    List<JobExecutor> getListByGroup(String group);
}
