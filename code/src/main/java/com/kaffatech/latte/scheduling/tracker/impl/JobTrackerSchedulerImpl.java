package com.kaffatech.latte.scheduling.tracker.impl;

import com.kaffatech.latte.message.MessageProducer;
import com.kaffatech.latte.message.MessageProducerFactory;
import com.kaffatech.latte.message.util.MessageUtils;
import com.kaffatech.latte.scheduling.dmo.type.JobInstanceStatus;
import com.kaffatech.latte.scheduling.message.JobMessage;
import com.kaffatech.latte.scheduling.tracker.JobInstanceManager;
import com.kaffatech.latte.scheduling.tracker.JobManager;
import com.kaffatech.latte.scheduling.tracker.JobTrackerScheduler;
import com.kaffatech.latte.scheduling.tracker.dmo.Job;
import com.kaffatech.latte.scheduling.tracker.dmo.JobInstance;
import com.kaffatech.latte.scheduling.tracker.dmo.JobTrackerContext;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTrackerSchedulerImpl implements JobTrackerScheduler {

    @Resource
    private JobManager jobManager;

    @Resource
    private JobInstanceManager jobInstanceManager;

    @Resource
    private MessageProducerFactory messageProducerFactory;

    @Override
    public void schedule(JobTrackerContext context) {
        List<JobInstance> instList = jobInstanceManager.getPendingList(context.getShardingId());
        for (JobInstance each : instList) {
            process(each);
        }
    }

    private void process(JobInstance instance) {
        Job job = jobManager.getJob(instance.getName(), instance.getGroup());

        // 更新实例数据
        updateJobInstance(instance);

        // 发送任务执行消息
        JobMessage msg = new JobMessage();
        msg.setName(instance.getName());
        msg.setGroup(instance.getGroup());
        msg.setData(instance.getData());
        MessageProducer producer = messageProducerFactory.create(job.getRunner());
        producer.send(MessageUtils.createObjectMessage(msg));
    }

    private void updateJobInstance(JobInstance instance) {
        Date now = new Date();
        JobInstance updatedJobInstance = new JobInstance();
        updatedJobInstance.setId(instance.getId());
        if (instance.getFirstRunTime() == null) {
            // 第一次执行
            updatedJobInstance.setFirstRunTime(now);
        }
        updatedJobInstance.setLastRunTime(now);
        updatedJobInstance.setStatus(JobInstanceStatus.PROCESSING);

        jobInstanceManager.update(updatedJobInstance);
    }
}
