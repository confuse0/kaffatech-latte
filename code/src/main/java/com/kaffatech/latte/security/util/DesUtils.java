package com.kaffatech.latte.security.util;

import com.kaffatech.latte.commons.toolkit.codec.Base64Utils;
import com.kaffatech.latte.commons.toolkit.codec.EncodingUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @author lingzhen on 16/8/13.
 */
public class DesUtils {

    public static final String ALGORITHM = "DES/ECB/PKCS5Padding";

    private static final String KEY_ALGORITHM = "DES";

    public static String encrypt(String key, String plainText) {
        byte[] keyBytes = Base64Utils.decode(key);
        byte[] textBytes = EncodingUtils.encode(plainText);
        byte[] encryptedBytes = encrypt(keyBytes, textBytes);
        return Base64Utils.encode(encryptedBytes);
    }


    public static String decrypt(String key, String encryptedText) {
        byte[] keyBytes = Base64Utils.decode(key);
        byte[] encryptedBytes = Base64Utils.decode(encryptedText);
        byte[] textBytes = decrypt(keyBytes, encryptedBytes);

        return EncodingUtils.decode(textBytes);
    }


    public static byte[] encrypt(byte[] key, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(key, KEY_ALGORITHM);

            // 加密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] decrypt(byte[] key, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(key, KEY_ALGORITHM);

            // 解密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getKey() {
        return Base64Utils.encode(getKeyBytes());
    }

    public static byte[] getKeyBytes() {
        //实例化密钥生成器
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        //初始化密钥生成器
        kg.init(56);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获取二进制密钥编码形式
        byte[] keyBytes = secretKey.getEncoded();
        if (keyBytes.length != 8) {
            throw new IllegalArgumentException("key length is invaid!");
        }
        return keyBytes;
    }

    public static void main(String[] args) throws Exception {
        String key = getKey();
        System.out.println(key);

        String plainText = "Hello World!";


        String encryptedText = encrypt(key, plainText);
        String text = decrypt(key, encryptedText);

        System.out.println(text);
    }

}
