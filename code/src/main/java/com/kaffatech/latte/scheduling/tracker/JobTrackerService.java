package com.kaffatech.latte.scheduling.tracker;

import com.kaffatech.latte.scheduling.dmo.type.JobInstanceStatus;

import java.util.Date;
import java.util.Map;

/**
 * @author lingzhen on 16/11/17.
 */
public interface JobTrackerService {

    void end(String instanceId, JobInstanceStatus status);
}
