package com.kaffatech.latte.ctx.id.model;

import com.kaffatech.latte.commons.bean.model.IdBean;

/**
 * @author lingzhen on 16/11/24.
 */
public class ShardingId extends IdBean {

    /**
     * 分片名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
