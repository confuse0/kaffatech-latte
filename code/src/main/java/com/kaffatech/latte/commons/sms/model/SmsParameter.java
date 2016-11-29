package com.kaffatech.latte.commons.sms.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author lingzhen on 16/11/9.
 */
public class SmsParameter extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String mobile;

    private String text;

    private String extendInfo;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}
