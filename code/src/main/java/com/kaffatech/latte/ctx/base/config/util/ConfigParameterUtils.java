package com.kaffatech.latte.ctx.base.config.util;

import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.ctx.base.config.ConfigParameterManager;

/**
 * @author lingzhen on 16/11/4.
 */
public class ConfigParameterUtils {

    private static final String DEF_NAME = "configParameterManager";

    public static String getParameter(String name) {
        return getManager().getParameter(name);
    }

    private static ConfigParameterManager getManager() {
        return (ConfigParameterManager) ApplicationContextHolder.getBean(DEF_NAME);
    }
}
