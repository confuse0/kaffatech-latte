package com.kaffatech.latte.security.util;

import com.kaffatech.latte.commons.toolkit.codec.Base64Utils;
import com.kaffatech.latte.commons.toolkit.base.math.ByteUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author lingzhen on 16/8/17.
 */
public class PasswordUtils {

    private static String FIX_SALT = "Z/66VA5U1q0=";

    /**
     * 产生随机盐值
     * @return
     */
    public static String generateSalt() {
        return DesUtils.getKey();
    }

    /**
     * 产生密码摘要
     * @param pwd
     * @param salt
     * @return
     */
    public static String generatePwdDigest(String pwd, String salt) {
        if (StringUtils.isEmpty(pwd)) {
            throw new IllegalArgumentException("password is not empty!");
        }

        byte[] pwdBytes = null;
        try {
            pwdBytes = pwd.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] saltBytes = Base64Utils.decode(salt);
        byte[] fixSaltBytes = Base64Utils.decode(FIX_SALT);

        long mixSaltLong = ByteUtils.bytesToLong(saltBytes) + ByteUtils.bytesToLong(fixSaltBytes);
        byte[] mixSaltBytes = ByteUtils.longToBytes(mixSaltLong);

        byte[] bytes = concatAll(pwdBytes, mixSaltBytes);

        return MessageDigestUtils.generateMd5(bytes);
    }

    /**
     * 判断密码是否有效
     * @param pwdDigest
     * @param pwd
     * @param salt
     * @return
     */
    public static boolean isCorrectPwd(String pwdDigest, String pwd, String salt) {
        String expectPwdDigest = generatePwdDigest(pwd, salt);
        return StringUtils.equals(pwdDigest, expectPwdDigest);
    }

    private static byte[] concatAll(byte[] first, byte[]... others) {
        int totalLength = first.length;
        for (byte[] array : others) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : others) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
