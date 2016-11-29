package com.kaffatech.latte.message.local;

import com.kaffatech.latte.message.MessageConsumer;
import com.kaffatech.latte.message.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lingzhen on 16/9/2.
 */
public class LocalTopic implements Topic {

    /**
     * 主题名称
     */
    private String topicName;

    /**
     * 消息消费者列表
     */
    private List<MessageConsumer> messageConsumerList = new ArrayList<MessageConsumer>();

    @Override
    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<MessageConsumer> getMessageConsumerList() {
        return messageConsumerList;
    }

    public void setMessageConsumerList(List<MessageConsumer> messageConsumerList) {
        this.messageConsumerList = messageConsumerList;
    }
}
