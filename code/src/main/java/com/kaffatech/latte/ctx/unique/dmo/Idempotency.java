package com.kaffatech.latte.ctx.unique.dmo;

import com.kaffatech.latte.commons.bean.model.IdBean;

/**
 * @author lingzhen on 16/11/7.
 */
public class Idempotency extends IdBean {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 唯一健拥有者
     */
    private String owner;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 唯一健
     */
    private String uniqueNo;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }
}
