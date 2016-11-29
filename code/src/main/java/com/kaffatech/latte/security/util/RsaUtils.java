package com.kaffatech.latte.security.util;

import com.kaffatech.latte.commons.toolkit.codec.Base64Utils;
import com.kaffatech.latte.commons.toolkit.codec.EncodingUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lingzhen on 16/8/13.
 */
public class RsaUtils {

    public static final String ALGORITHMS = "RSA";

    private static final String KEY_ALGORITHM = "RSA";

    public static final String PUBLIC_KEY_NAME = "publicKey";
    public static final String PRIVATE_KEY_NAME = "privateKey";

    public static final int KEY_SIZE = 1024;

    public static Map<String, byte[]> genKeyPairBytes() {
        Map<String, byte[]> keyMap = new HashMap<String, byte[]>();

        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            gen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair pair = gen.generateKeyPair();

            keyMap.put(PUBLIC_KEY_NAME, pair.getPublic().getEncoded());
            keyMap.put(PRIVATE_KEY_NAME, pair.getPrivate().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        return keyMap;
    }

    public static Map<String, String> genKeyPair() {
        Map<String, String> strKeyMap = new HashMap<String, String>();
        Map<String, byte[]> keyMap = genKeyPairBytes();
        strKeyMap.put(PUBLIC_KEY_NAME, Base64Utils.encode(keyMap.get(PUBLIC_KEY_NAME)));
        strKeyMap.put(PRIVATE_KEY_NAME, Base64Utils.encode(keyMap.get(PRIVATE_KEY_NAME)));

        return strKeyMap;
    }

    public static String encryptByPublicKey(String publicKey, String plainText) {
        byte[] publicKeyBytes = Base64Utils.decode(publicKey);
        byte[] textBytes = EncodingUtils.encode(plainText);
        byte[] encryptedBytes = encryptByPublicKey(publicKeyBytes, textBytes);

        return Base64Utils.encode(encryptedBytes);
    }

    public static byte[] encryptByPublicKey(byte[] publicKeyBytes, byte[] plainText) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyBytes);
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plainText);

            return encryptedBytes;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String decryptByPrivateKey(String privatKey, String encryptedText) {
        byte[] privateKeyBytes = Base64Utils.decode(privatKey);
        byte[] encryptedBytes = Base64Utils.decode(encryptedText);
        byte[] decryptedBytes = decryptByPrivateKey(privateKeyBytes, encryptedBytes);

        return EncodingUtils.decode(decryptedBytes);
    }

    public static byte[] decryptByPrivateKey(byte[] privateKeyBytes, byte[] encryptedBytes) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyBytes);
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return decryptedBytes;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static PublicKey getPublicKey(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static PrivateKey getPrivateKey(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = factory
                .generatePrivate(pkcs8EncodedKeySpec);
        return privateKey;
    }

    public static void main(String[] args) {
        Map<String, String> keyPair = genKeyPair();
        System.out.println(keyPair.get(PUBLIC_KEY_NAME));
        System.out.println(keyPair.get(PRIVATE_KEY_NAME));
    }

}
