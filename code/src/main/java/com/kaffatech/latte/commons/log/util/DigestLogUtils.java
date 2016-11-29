package com.kaffatech.latte.commons.log.util;

import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.commons.log.Digest;
import com.kaffatech.latte.commons.log.DigestLogManager;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

/**
 * @author lingzhen on 16/11/14.
 */
public class DigestLogUtils {

    private static final String DEF_NAME = "digestLogManager";

    public static void info(Logger logger, Digest digest) {
        if (logger.isInfoEnabled()) {
            logger.info(digest.toString());
        }

        if (BooleanUtils.toBoolean(SystemProperties.getProperty("digest-persistent"))) {
            getDigestLogManager().save(digest);
        }
    }

    private static DigestLogManager getDigestLogManager() {
        return (DigestLogManager) ApplicationContextHolder.getBean(DEF_NAME);
    }

}
