package com.kaffatech.latte.scheduling.tracker;

import com.kaffatech.latte.scheduling.tracker.model.Job;

/**
 * @author lingzhen on 16/11/15.
 */
public interface JobManager {

    Job getJob(String name, String group);
}
