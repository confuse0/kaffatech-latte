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
    private String name;

    /**
     * KEY
     */
    private String key;

    /**
     * 算法
     */
    private String algorithms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(String algorithms) {
        this.algorithms = algorithms;
    }
}
