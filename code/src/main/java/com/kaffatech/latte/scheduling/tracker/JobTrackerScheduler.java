package com.kaffatech.latte.scheduling.tracker;

import com.kaffatech.latte.scheduling.tracker.dmo.JobTrackerContext;

/**
 * @author lingzhen on 16/11/17.
 */
public interface JobTrackerScheduler {

    void schedule(JobTrackerContext context);
}
