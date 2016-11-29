package com.kaffatech.latte.ctx.cc.model;

import com.kaffatech.latte.commons.bean.model.IdBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lingzhen on 16/11/21.
 */
public class Cluster extends IdBean {

    /**
     * 版本序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 主服务器名
     */
    private String master;

    /**
     * 分配时间
     */
    private Date lastAllocateTime;

    /**
     * 配置数据
     */
    private Map<String, String> data;

    /**
     * 服务器列表
     */
    private List<Server> serverList;

    /**
     * 服务器列表版本号
     */
    private Long ver;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public Date getLastAllocateTime() {
        return lastAllocateTime;
    }

    public void setLastAllocateTime(Date lastAllocateTime) {
        this.lastAllocateTime = lastAllocateTime;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
