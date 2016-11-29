
package com.kaffatech.latte.security.util;

import com.kaffatech.latte.commons.toolkit.base.math.ByteUtils;
import com.kaffatech.latte.commons.toolkit.codec.EncodingUtils;

import java.security.MessageDigest;

/**
 * @author zhen.ling
 *
 */
public class MessageDigestUtils {

	private static final String SHA1 = "SHA-1";

	private static final String MD5 = "MD5";

	public static String generate16Md5(String str) {
		return generateMd5(str).substring(8, 24);
	}

	public static String generateMd5(String str) {
		return generateMd5(getBytes(str));
	}

	public static String generateMd5(byte[] btInput) {
		return ByteUtils.byteToHexString(generateMd5Bytes(btInput));
	}

	private static byte[] generateMd5Bytes(byte[] btInput) {
		return generateMessageDigestBytes(btInput, MD5);
	}

	public static String generateSha1(String str) {
		return generateSha1(getBytes(str));
	}

	public static String generateSha1(byte[] btInput) {
		return ByteUtils.byteToHexString(generateSha1Bytes(btInput));
	}

	private static byte[] generateSha1Bytes(byte[] btInput) {
		return generateMessageDigestBytes(btInput, SHA1);
	}

	private static byte[] getBytes(String str) {
		return EncodingUtils.encode(str);
	}

	private static byte[] generateMessageDigestBytes(byte[] btInput, String algorithm) {
		byte[] md5 = null;
		try {
			MessageDigest mdInst = MessageDigest.getInstance(algorithm);
			mdInst.update(btInput);
			md5 = mdInst.digest();
		} catch (Exception e) {
			throw new IllegalStateException("no " + algorithm + " algorithm");
		}
		return md5;
	}
}
