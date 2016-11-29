package com.kaffatech.latte.message.local;

import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.message.MessageProducer;
import com.kaffatech.latte.message.MessageProducerFactory;

/**
 * @author lingzhen on 16/11/16.
 */
public class LocalMessageProducerFactory implements MessageProducerFactory {

    @Override
    public MessageProducer create(String topicName) {
        LocalTopic topic = (LocalTopic) ApplicationContextHolder.getBean(topicName);

        LocalMessageProducer producer = new LocalMessageProducer();
        producer.setTopic(topic);
        return producer;
    }
}
