package com.kaffatech.latte.scheduling.impl;

import com.kaffatech.latte.scheduling.JobTemplate;

import java.util.Date;
import java.util.Map;

/**
 * @author lingzhen on 16/11/15.
 */
public class JobTemplateImpl implements JobTemplate {

    @Override
    public String submit(String name, Map<?, ?> data, Date minExecutableTime, String executor) {
        return null;
    }

    @Override
    public void close(String instanceId) {

    }
}
