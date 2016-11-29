package com.kaffatech.latte.security.util;

import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.security.KeyGenerator;

/**
 * @author lingzhen on 16/11/3.
 */
public class KeyUtils {

    private static final String DEF_NAME = "keyGenerator";

    public static final String SIMPLE_CRYPT_KEY_NAME = "simpleCryptKey";

    public static final String CRYPT_KEY_NAME = "cryptKey";

    public static final String CRYPT_PUBLIC_KEY_NAME = "cryptPublicKey";

    public static final String CRYPT_PRIVATE_KEY_NAME = "cryptPrivateKey";

    public static final String SIGN_PUBLIC_KEY_NAME = "signPublicKey";

    public static final String SIGN_PRIVATE_KEY_NAME = "signPrivateKey";

    public static String getSimpleCryptKey() {
        return getKeyGenerator().getKey(SIMPLE_CRYPT_KEY_NAME);
    }

    public static String getCryptKey() {
        return getKeyGenerator().getKey(CRYPT_KEY_NAME);
    }

    public static String getCryptPublicKey() {
        return getKeyGenerator().getKey(CRYPT_PUBLIC_KEY_NAME);
    }

    public static String getCryptPrivateKey() {
        return getKeyGenerator().getKey(CRYPT_PRIVATE_KEY_NAME);
    }

    public static String getSignPublicKey() {
        return getKeyGenerator().getKey(SIGN_PUBLIC_KEY_NAME);
    }

    public static String getSignPrivateKey() {
        return getKeyGenerator().getKey(SIGN_PRIVATE_KEY_NAME);
    }

    public static String getKey(String name) {
        return getKeyGenerator().getKey(name);
    }

    private static KeyGenerator getKeyGenerator() {
        return (KeyGenerator) ApplicationContextHolder.getBean(DEF_NAME);
    }

}
