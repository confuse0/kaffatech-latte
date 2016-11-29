package com.kaffatech.latte.ctx.base;

import java.util.Map;

/**
 * Created by lingzhen on 16/3/31.
 */
public class CurrentContextHolder {

    private static ThreadLocal<Map<String, Object>> PARAM = new ThreadLocal<Map<String, Object>>();


    public static void put(String key, Object value) {
        PARAM.get().put(key, value);
    }

    public static Object get(String key) {
        return PARAM.get().get(key);
    }

    public static void clear() {
        PARAM.remove();
    }

}
