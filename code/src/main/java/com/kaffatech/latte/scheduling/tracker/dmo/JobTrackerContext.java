package com.kaffatech.latte.scheduling.tracker.dmo;

import com.kaffatech.latte.commons.bean.model.BaseBean;

import java.util.Map;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTrackerContext extends BaseBean {

    /**
     * Job名称
     */
    private String name;

    /**
     * Job分组
     */
    private String group;

    /**
     * 分片ID
     */
    private String shardingId;

    /**
     * 数据
     */
    private Map<?, ?> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getShardingId() {
        return shardingId;
    }

    public void setShardingId(String shardingId) {
        this.shardingId = shardingId;
    }

    public Map<?, ?> getData() {
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }
}
