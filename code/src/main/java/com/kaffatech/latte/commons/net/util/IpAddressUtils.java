package com.kaffatech.latte.commons.net.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lingzhen on 16/10/31.
 */
public class IpAddressUtils {

    public static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }
}
