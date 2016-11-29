package com.kaffatech.latte.scheduling.tracker.quartz;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;

/**
 * @author lingzhen on 16/11/15.
 */
public class MessageJobDetail implements JobDetail {

    /**
     * 任务名
     */
    private String name;

    /**
     * 任务分组名
     */
    private String group;

    /**
     * 任务Key
     */
    private JobKey key;

    /**
     * 任务类
     */
    private Class<? extends Job> jobClass = MessageJob.class;

    public MessageJobDetail(String name, String group) {
        this.name = name;
        this.group = group;
        key = new JobKey(name, group);
    }

    @Override
    public JobKey getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return name +"@" + group;
    }

    @Override
    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    @Override
    public JobDataMap getJobDataMap() {
        return null;
    }

    @Override
    public boolean isDurable() {
        return false;
    }

    @Override
    public boolean isPersistJobDataAfterExecution() {
        return false;
    }

    @Override
    public boolean isConcurrentExectionDisallowed() {
        return false;
    }

    @Override
    public boolean requestsRecovery() {
        return false;
    }

    @Override
    public JobBuilder getJobBuilder() {
        JobBuilder b = JobBuilder.newJob()
                .ofType(getJobClass())
                .requestRecovery(requestsRecovery())
                .storeDurably(isDurable())
                .usingJobData(getJobDataMap())
                .withDescription(getDescription())
                .withIdentity(getKey());
        return b;
    }

    public Object clone() {
        return null;
    }

}
