package com.kaffatech.latte.scheduling;

import java.util.Date;
import java.util.Map;

/**
 * @author lingzhen on 16/11/15.
 */
public interface JobTemplate {

    String submit(String name, Map<?, ?> data, Date minExecutableTime, String executor);

    void close(String instanceId);
}
