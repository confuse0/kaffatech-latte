package com.kaffatech.latte.security.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author lingzhen on 17/2/22.
 */
public class KeyInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**
     * 名字
     */
    private String keyName;

    /**
     * KEY值
     */
    private String keyValue;

    /**
     * 算法
     */
    private String algorithms;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(String algorithms) {
        this.algorithms = algorithms;
    }
}
