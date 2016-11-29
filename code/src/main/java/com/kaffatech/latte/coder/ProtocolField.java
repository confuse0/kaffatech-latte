package com.kaffatech.latte.coder;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

/**
 * Created by lingzhen on 15/12/25.
 */
public class ProtocolField extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 字段名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 必填
     */
    private String must;

    /**
     * 大小
     */
    private String size;

    /**
     * 格式
     */
    private String format;

    /**
     * 说明
     */
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReadName() {
        return "get" + StringUtils.upperFc(name);
    }

    public String getWriteName() {
        return "set" + StringUtils.upperFc(name);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMust() {
        return must;
    }

    public void setMust(String must) {
        this.must = must;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
