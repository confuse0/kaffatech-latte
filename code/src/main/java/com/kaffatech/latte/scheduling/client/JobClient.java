package com.kaffatech.latte.scheduling.client;

import java.util.Date;
import java.util.Map;

/**
 * @author lingzhen on 16/11/17.
 */
public interface JobClient {

    String submit(String name, String group, Map<?, ?> data, Date minRunnableTime);

    void close(String instanceId);
}
