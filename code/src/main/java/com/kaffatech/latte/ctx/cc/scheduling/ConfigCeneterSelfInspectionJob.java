package com.kaffatech.latte.ctx.cc.scheduling;

import com.kaffatech.latte.ctx.cc.ConfigCenterManager;
import com.kaffatech.latte.scheduling.SerializationJob;

/**
 * @author lingzhen on 17/4/11.
 */
public class ConfigCeneterSelfInspectionJob extends SerializationJob {

    /**
     * 配置中心管理器
     */
    private ConfigCenterManager configCenterManager;

    @Override
    protected void process() {
        configCenterManager.monitorConfigCenter();
    }

    public void setConfigCenterManager(ConfigCenterManager configCenterManager) {
        this.configCenterManager = configCenterManager;
    }
}
