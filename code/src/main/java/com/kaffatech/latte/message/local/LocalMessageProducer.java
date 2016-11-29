package com.kaffatech.latte.message.local;


import com.kaffatech.latte.message.Message;
import com.kaffatech.latte.message.MessageConsumer;
import com.kaffatech.latte.message.MessageListener;
import com.kaffatech.latte.message.MessageProducer;

import java.util.List;

/**
 * @author lingzhen on 16/9/2.
 */
public class LocalMessageProducer implements MessageProducer {

    private LocalTopic topic;

    @Override
    public void send(Message message) {
        List<MessageConsumer> msgConsumerList =  topic.getMessageConsumerList();
        for (MessageConsumer consumer : msgConsumerList) {
            MessageListener msgListener = consumer.getMessageListener();
            if (msgListener != null) {
                msgListener.onMessage(message);
            }
        }
    }

    public void setTopic(LocalTopic topic) {
        this.topic = topic;
    }
}
