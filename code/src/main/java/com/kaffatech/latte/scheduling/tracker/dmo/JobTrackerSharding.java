package com.kaffatech.latte.scheduling.tracker.dmo;

import com.kaffatech.latte.commons.bean.model.BaseBean;

import java.util.Map;

/**
 * @author lingzhen on 16/11/18.
 */
public class JobTrackerSharding extends BaseBean {

    /**
     * JobTracker集群版本号
     */
    private Long ver;

    /**
     * 集群Map
     */
    private Map<String, JobTracker> jobTrackerMap;

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public Map<String, JobTracker> getJobTrackerMap() {
        return jobTrackerMap;
    }

    public void setJobTrackerMap(Map<String, JobTracker> jobTrackerMap) {
        this.jobTrackerMap = jobTrackerMap;
    }
}
