package com.kaffatech.latte.mainframe.util;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.commons.toolkit.regex.RegexUtils;

/**
 * Created by lingzhen on 16/5/25.
 */
public class MobileUtils {

    public static String getCrypticMobile(String mobile) {
        if (StringUtils.isEmpty(mobile) || mobile.length() < 8) {
            return mobile;
        }

        char[] mobileCharArray = mobile.toCharArray();
        mobileCharArray[mobileCharArray.length - 5] = '*';
        mobileCharArray[mobileCharArray.length - 6] = '*';
        mobileCharArray[mobileCharArray.length - 7] = '*';
        mobileCharArray[mobileCharArray.length - 8] = '*';

        return new String(mobileCharArray);
    }

    public static String getTailNumber(String mobile) {
        return getTailNumber(mobile, null);
    }

    public static String getTailNumber(String mobile, String prev) {
        if (StringUtils.isEmpty(mobile) || mobile.length() < 4) {
            return mobile;
        }

        String tailNum = mobile.substring(mobile.length() - 4);
        if (!StringUtils.isEmpty(prev)) {
            tailNum = prev + tailNum;
        }
        return tailNum;
    }

    public static boolean isNumCorrect(String mobile) {
        String pattern = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
        return RegexUtils.match(mobile, pattern);
    }

    public static void main(String[] args) {
        System.out.println(getCrypticMobile("13488817975"));
        System.out.println(getTailNumber("13488817975"));
        System.out.println(getTailNumber("13488817975", "用户"));
    }
}
