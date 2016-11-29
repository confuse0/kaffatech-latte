package com.kaffatech.latte.scheduling.message;

import com.kaffatech.latte.commons.bean.model.BaseBean;

import java.util.Map;

/**
 * @author lingzhen on 16/11/15.
 */
public class JobMessage extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**
     * Job名称
     */
    private String name;

    /**
     * Job分组
     */
    private String group;

    /**
     * 任务数据
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

    public Map<?, ?> getData() {
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }
}
