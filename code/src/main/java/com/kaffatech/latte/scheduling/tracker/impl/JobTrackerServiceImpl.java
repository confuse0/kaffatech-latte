package com.kaffatech.latte.scheduling.tracker.impl;

import com.kaffatech.latte.scheduling.dmo.type.JobInstanceStatus;
import com.kaffatech.latte.scheduling.tracker.JobInstanceManager;
import com.kaffatech.latte.scheduling.tracker.JobTrackerService;
import com.kaffatech.latte.scheduling.tracker.model.JobInstance;

import javax.annotation.Resource;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTrackerServiceImpl implements JobTrackerService {

    @Resource
    private JobInstanceManager jobInstanceManager;


    @Override
    public void end(String instanceId, JobInstanceStatus status) {
        JobInstance inst = jobInstanceManager.getById(Long.valueOf(instanceId));
        inst.setStatus(status);
        jobInstanceManager.update(inst);
    }
}
