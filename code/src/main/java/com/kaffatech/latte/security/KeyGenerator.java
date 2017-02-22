package com.kaffatech.latte.security;

import com.kaffatech.latte.security.model.KeyInfo;

/**
 * @author lingzhen on 16/11/3.
 */
public interface KeyGenerator {

    KeyInfo getKey(String name);
}
