package com.kaffatech.latte.message;

import java.io.Serializable;

/**
 * @author lingzhen on 16/9/2.
 */
public interface ObjectMessage extends Message {

    Serializable getObject();
}
