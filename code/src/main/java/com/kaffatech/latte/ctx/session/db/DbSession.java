package com.kaffatech.latte.ctx.session.db;

import org.springframework.session.ExpiringSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingzhen on 16/10/11.
 */
public class DbSession implements ExpiringSession {

    /**
     * SESSION_ID
     */
    private String id;

    /**
     * 创建时间
     */
    private long creationTime;

    /**
     * 上一次访问时间
     */
    private volatile long lastAccessedTime;

    /**
     * 时间间隔
     */
    private int maxInactiveIntervalInSeconds;

    /**
     * SESSION数据
     */
    private Map<String, Object> data = new ConcurrentHashMap<String, Object>();

    DbSession() {
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = this.creationTime;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public void setMaxInactiveIntervalInSeconds(int maxInactiveIntervalInSeconds) {
        this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
    }

    @Override
    public int getMaxInactiveIntervalInSeconds() {
        return maxInactiveIntervalInSeconds;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public <T> T getAttribute(String name) {
        return (T) data.get(name);
    }

    @Override
    public Set<String> getAttributeNames() {
        return data.keySet();
    }

    @Override
    public void setAttribute(String name, Object value) {
        data.put(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        data.remove(name);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
