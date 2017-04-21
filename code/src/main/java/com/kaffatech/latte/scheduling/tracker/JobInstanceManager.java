package com.kaffatech.latte.scheduling.tracker;

import com.kaffatech.latte.scheduling.tracker.model.JobInstance;

import java.util.List;

/**
 * @author lingzhen on 16/11/15.
 */
public interface JobInstanceManager {

    long save(JobInstance instance);

    int update(JobInstance instance);

    JobInstance getById(long id);

    List<JobInstance> getPendingList(String shardingId);
}
