package com.kaffatech.latte.message;

/**
 * @author lingzhen on 16/9/2.
 */
public interface MessageProducerFactory {

    MessageProducer create(String topicName);
}
