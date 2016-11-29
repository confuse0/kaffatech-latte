package com.kaffatech.latte.scheduling.tracker.dmo;

import com.kaffatech.latte.commons.bean.model.IdBean;

import java.util.List;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobTracker extends IdBean {

    private static final long serialVersionUID = 1L;

    /**
     * JobTracker名
     */
    private String name;

    /**
     * Job分片组
     */
    private List<String> shardingGroup;

    /**
     * 分片版本号
     */
    private Long shardingVer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getShardingGroup() {
        return shardingGroup;
    }

    public void setShardingGroup(List<String> shardingGroup) {
        this.shardingGroup = shardingGroup;
    }

    public Long getShardingVer() {
        return shardingVer;
    }

    public void setShardingVer(Long shardingVer) {
        this.shardingVer = shardingVer;
    }
}
