package com.kaffatech.latte.commons.net.http.util;

import javax.servlet.http.Cookie;

/**
 * Created by lingzhen on 16/5/18.
 */
public class CookieUtils {

    private static final String DEF_PATH = "/";
    private static final int DEF_MAX_AGE = -1;

    public static Cookie generateCookie(String key, String value, String domain) {
        return generateCookie(key, value, domain, DEF_PATH, DEF_MAX_AGE);
    }

    public static Cookie generateCookie(String key, String value, String domain, String path) {
        return generateCookie(key, value, domain, path, DEF_MAX_AGE);
    }

    public static Cookie generateCookie(String key, String value, String domain, int maxAge) {
        return generateCookie(key, value, domain, DEF_PATH, maxAge);
    }

    public static Cookie generateCookie(String key, String value, String domain, String path, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);

        return cookie;
    }

}
