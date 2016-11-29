package com.kaffatech.latte.message.local;

import com.kaffatech.latte.message.MessageConsumer;
import com.kaffatech.latte.message.MessageListener;

/**
 * @author lingzhen on 16/9/2.
 */
public class LocalMessageConsumer implements MessageConsumer {

    private MessageListener messageListener;

    @Override
    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
}
