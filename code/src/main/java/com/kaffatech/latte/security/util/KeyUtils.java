package com.kaffatech.latte.security.util;

import com.kaffatech.latte.ctx.base.SystemProperties;

/**
 * @author lingzhen on 16/11/3.
 */
public class KeyUtils {

    public static final String SIMPLE_CRYPT_KEY_NAME = "simpleCryptKey";

    public static final String CRYPT_KEY_NAME = "cryptKey";

    public static final String CRYPT_PUBLIC_KEY_NAME = "cryptPublicKey";

    public static final String CRYPT_PRIVATE_KEY_NAME = "cryptPrivateKey";

    public static final String SIGN_PUBLIC_KEY_NAME = "signPublicKey";

    public static final String SIGN_PRIVATE_KEY_NAME = "signPrivateKey";

    public static String getSimpleCryptKey() {
        return SystemProperties.getProperty(SIMPLE_CRYPT_KEY_NAME);
    }

    public static String getCryptKey() {
        return SystemProperties.getProperty(CRYPT_KEY_NAME);
    }

    public static String getCryptPublicKey() {
        return SystemProperties.getProperty(CRYPT_PUBLIC_KEY_NAME);
    }

    public static String getCryptPrivateKey() {
        return SystemProperties.getProperty(CRYPT_PRIVATE_KEY_NAME);
    }

    public static String getSignPublicKey() {
        return SystemProperties.getProperty(SIGN_PUBLIC_KEY_NAME);
    }

    public static String getSignPrivateKey() {
        return SystemProperties.getProperty(SIGN_PRIVATE_KEY_NAME);
    }

    public static String getKeyOfKey() {
        return SystemProperties.getKeyOfKey();
    }

}
