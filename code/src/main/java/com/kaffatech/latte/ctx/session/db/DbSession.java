package com.kaffatech.latte.ctx.session.db;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import org.springframework.session.ExpiringSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingzhen on 16/10/11.
 */
public class DbSession extends BaseBean implements ExpiringSession {

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
    private Map<String, Object> sessionData = new ConcurrentHashMap<String, Object>();

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
        return (T) sessionData.get(name);
    }

    @Override
    public Set<String> getAttributeNames() {
        return sessionData.keySet();
    }

    @Override
    public void setAttribute(String name, Object value) {
        sessionData.put(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        sessionData.remove(name);
    }

    public Map<String, Object> getSessionData() {
        return sessionData;
    }

    public void setSessionData(Map<String, Object> data) {
        this.sessionData = data;
    }
}
