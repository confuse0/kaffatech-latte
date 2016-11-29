package com.kaffatech.latte.message.impl;


import com.kaffatech.latte.message.ObjectMessage;

import java.io.Serializable;

/**
 * @author lingzhen on 16/9/2.
 */
public class SimpleObjectMessage implements ObjectMessage {

    private Serializable object;

    @Override
    public Serializable getObject() {
        return object;
    }

    public void setObject(Serializable object) {
        this.object = object;
    }
}
