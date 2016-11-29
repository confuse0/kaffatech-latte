package com.kaffatech.latte.commons.sms.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author lingzhen on 16/11/9.
 */
public class SmsResult extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 扩展字段
     */
    private String extendInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}
