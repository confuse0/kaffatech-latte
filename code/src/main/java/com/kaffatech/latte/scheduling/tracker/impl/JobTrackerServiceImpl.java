package com.kaffatech.latte.scheduling.tracker.impl;

import com.kaffatech.latte.ctx.id.IdGenerator;
import com.kaffatech.latte.scheduling.dmo.type.JobInstanceStatus;
import com.kaffatech.latte.scheduling.tracker.JobInstanceManager;
import com.kaffatech.latte.scheduling.tracker.JobManager;
import com.kaffatech.latte.scheduling.tracker.JobTrackerManager;
import com.kaffatech.latte.scheduling.tracker.JobTrackerService;
import com.kaffatech.latte.scheduling.tracker.dmo.JobInstance;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTrackerServiceImpl implements JobTrackerService {

    @Resource
    private JobManager jobManager;

    @Resource
    private JobTrackerManager jobTrackerManager;

    @Resource
    private JobInstanceManager jobInstanceManager;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public String submit(String name, String group, Map<?, ?> data, Date minRunnableTime) {
        // 计算分片
        String shardingId = jobTrackerManager.allocateShardingId(IpAddressUtils.getHostAddress());

        // 保存任务实例
        JobInstance inst = new JobInstance();
        inst.setId(idGenerator.next());
        inst.setName(name);
        inst.setGroup(group);
        inst.setShardingId(shardingId);
        inst.setData(data);
        inst.setMinRunutableTime(minRunnableTime);
        inst.setStatus(JobInstanceStatus.PENDING);

        jobInstanceManager.save(inst);

        return String.valueOf(inst.getId());
    }

    @Override
    public void close(String instanceId) {
        JobInstance inst = jobInstanceManager.getById(Long.valueOf(instanceId));
        inst.setStatus(JobInstanceStatus.CLOSED);
        jobInstanceManager.update(inst);
    }
}
