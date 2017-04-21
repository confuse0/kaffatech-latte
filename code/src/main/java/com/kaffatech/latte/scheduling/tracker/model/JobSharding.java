package com.kaffatech.latte.scheduling.tracker.model;

import com.kaffatech.latte.commons.bean.model.IdBean;

import java.util.List;

/**
 * @author lingzhen on 16/11/17.
 */
public class JobSharding extends IdBean {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器名
     */
    private String serverName;

    /**
     * Job分片组
     */
    private String shardingGroup;

    /**
     * 分片版本号
     */
    private Long shardingVer;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getShardingGroup() {
        return shardingGroup;
    }

    public void setShardingGroup(String shardingGroup) {
        this.shardingGroup = shardingGroup;
    }

    public Long getShardingVer() {
        return shardingVer;
    }

    public void setShardingVer(Long shardingVer) {
        this.shardingVer = shardingVer;
    }

}
