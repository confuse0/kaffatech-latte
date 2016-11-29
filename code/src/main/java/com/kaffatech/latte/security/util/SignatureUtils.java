package com.kaffatech.latte.security.util;

import com.kaffatech.latte.commons.bean.util.BeanUtils;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.log.util.LogUtils;
import com.kaffatech.latte.commons.toolkit.codec.Base64Utils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.commons.toolkit.codec.EncodingUtils;

import java.beans.PropertyDescriptor;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author lingzhen on 16/10/27.
 */
public class SignatureUtils {

    public static final String DEF_SIGN_TYPE = "RSA";

    public static final String DEF_SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String DEF_KEY_FACTORY = "RSA";

    public static String sign(String privateKey, Object arg) {
        return SignatureUtils.sign(privateKey, getContent(arg));
    }

    public static boolean verify(String publicKey, Object arg, String sign) {
        return SignatureUtils.verify(publicKey, getContent(arg), sign);
    }

    public static String getContent(Object arg) {
        PropertyDescriptor[] fields = BeanUtils.getPropertyDescriptors(arg.getClass());
        Map<String, String> dataSignMap = new TreeMap<String, String>();
        for (PropertyDescriptor field : fields) {
            String key = field.getName();
            String value = BeanUtils.getPropertyString(arg, key);
            dataSignMap.put(key, value);
        }

        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, String> entry : dataSignMap.entrySet()) {
            if (!StringUtils.equals("sign", entry.getKey()) && !StringUtils.equals("signType", entry.getKey()) && !StringUtils.equals("class", entry.getKey())) {
                if (!StringUtils.isEmpty(entry.getValue())) {
                    signSb.append(entry.getKey());
                    signSb.append("=");
                    signSb.append(entry.getValue());
                    signSb.append("&");
                }
            }
        }
        // 删除最后一个连接符
        if (signSb.length() > 0) {
            signSb.deleteCharAt(signSb.length() - 1);
        }
        return signSb.toString();
    }

    /**
     * RSA签名
     *
     * @param privateKey 私钥
     * @param content 待签名数据
     * @return
     */
    public static String sign(String privateKey, String content) {
        return sign(privateKey, content, EncodingUtils.DEFAULT);
    }

    /**
     * RSA签名
     *
     * @param privateKey 私钥
     * @param content    待签名数据
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String privateKey, String content, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));

            KeyFactory key = KeyFactory.getInstance(DEF_KEY_FACTORY);
            PrivateKey priKey = key.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(DEF_SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(encode));

            byte[] signed = signature.sign();

            return Base64Utils.encode(signed);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * RSA验签名检查
     *
     * @param publicKey 公钥
     * @param content 待签名数据
     * @param sign 签名值
     * @return
     */
    public static boolean verify(String publicKey, String content, String sign) {
        return verify(publicKey, content, sign, EncodingUtils.DEFAULT);
    }

    /**
     * RSA验签名检查
     *
     * @param publicKey 公钥
     * @param content   待签名数据
     * @param sign      签名值
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean verify(String publicKey, String content, String sign, String encode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(DEF_KEY_FACTORY);
            byte[] encodedKey = Base64Utils.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            java.security.Signature signature = java.security.Signature
                    .getInstance(DEF_SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));

            boolean bverify = signature.verify(Base64Utils.decode(sign));
            return bverify;

        } catch (Exception e) {
            LogUtils.warn(Log.WARN_LOGGER, "验签失败[" + sign + "]:" + content, e);
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> keyPair = RsaUtils.genKeyPair();
        String pk = keyPair.get(RsaUtils.PUBLIC_KEY_NAME);
        String sk = keyPair.get(RsaUtils.PRIVATE_KEY_NAME);

        String content = "你好 Hello world!";

        String sign = sign(sk, content);

        System.out.println(sign);

        boolean ok = verify(pk, content, sign);

        System.out.println(ok);
    }
}
