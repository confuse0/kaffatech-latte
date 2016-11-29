package com.kaffatech.latte.message;

/**
 * @author lingzhen on 16/9/2.
 */
public interface MessageListener {

    /**
     * 消息处理
     *
     * @param message
     */
    void onMessage(Message message);

}
