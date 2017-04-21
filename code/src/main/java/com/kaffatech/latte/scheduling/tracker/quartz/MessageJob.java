package com.kaffatech.latte.scheduling.tracker.quartz;

import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.message.MessageProducerFactory;
import com.kaffatech.latte.scheduling.tracker.JobInstanceManager;
import com.kaffatech.latte.scheduling.tracker.JobTrackerScheduler;
import com.kaffatech.latte.scheduling.tracker.model.JobTrackerContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * @author lingzhen on 16/11/15.
 */
public class MessageJob implements Job {

    @Resource
    private JobTrackerScheduler jobTrackerScheduler;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobTrackerContext ctx = new JobTrackerContext();
//        ctx.setName(context.getJobDetail().getKey().getName());
//        ctx.setGroup(context.getJobDetail().getKey().getGroup());
//        ctx.setShardingId(context.getJobDetail().getJobDataMap().getString("shardingId"));
//        ctx.setData(context.getJobDetail().getJobDataMap());

        jobTrackerScheduler.schedule(ctx);
    }


    private JobInstanceManager getJobInstanceManager() {
        return (JobInstanceManager) ApplicationContextHolder.getBean("jobInstanceManager");
    }

    private MessageProducerFactory getMessageProducerFactory() {
        return (MessageProducerFactory) ApplicationContextHolder.getBean("messageProducerFactory");
    }
}
