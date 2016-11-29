package com.kaffatech.latte.scheduling;

import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.log.util.LogUtils;

/**
 * @author lingzhen on 16/9/2.
 */
public abstract class SerializationJob implements Runnable {

    private volatile boolean isRunning = false;

    public void run() {
        LogUtils.info(Log.SCHEDULING_LOGGER, "[" + this.getClass() + "] BEGIN...");
        int status = 0; // 未运行
        if (!isRunning) {
            isRunning = true;
            status = 1; // 成功

            try {
                process();
            } catch (Throwable t) {
                System.out.println(this.getClass());
                System.out.println(t.getMessage());
                System.out.println("Failed...");
                status = 2; // 错误
            } finally {
                isRunning = false;
            }
        }
        LogUtils.info(Log.SCHEDULING_LOGGER, "[" + this.getClass() + "] END:" + status);
    }

    protected abstract void process();

}
