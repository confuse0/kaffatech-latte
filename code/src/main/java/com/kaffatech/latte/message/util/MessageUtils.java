package com.kaffatech.latte.message.util;

import com.kaffatech.latte.message.Message;
import com.kaffatech.latte.message.ObjectMessage;
import com.kaffatech.latte.message.impl.SimpleObjectMessage;

import java.io.Serializable;

/**
 * @author lingzhen on 16/9/2.
 */
public class MessageUtils {

    public static ObjectMessage createObjectMessage(Serializable obj) {
        SimpleObjectMessage som = new SimpleObjectMessage();
        som.setObject(obj);
        return som;
    }

    public static <T> T getObject(Message objMsg, Class<T> clazz) {
        if (objMsg == null) {
            return null;
        }
        return (T) ((ObjectMessage) objMsg).getObject();
    }
}
