
package com.kaffatech.latte.commons.toolkit.codec;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * @author zhen.ling
 *
 */
public class Base64Utils {

	private static final String ENCODING = "UTF-8";

	public static String encodeByString(String src) {
		String result = null;
		try {
			result = encode(src.getBytes(ENCODING));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String decodeByString(String base64Str) {
		String result = null;
		try {
			result = new String(decode(base64Str), ENCODING);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String encode(byte[] bytes) {
		Base64 base64 = new Base64();
		return base64.encodeToString(bytes);
	}

	public static byte[] decode(String base64Str) {
		Base64 base64 = new Base64();
		return base64.decode(base64Str);
	}

	public static String encodeByStringForUrlSafe(String src) {
		String result = null;
		try {
			result = encodeForUrlSafe(src.getBytes(ENCODING));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String decodeByStringForUrlSafe(String base64Str) {
		String result = null;
		try {
			result = new String(decodeForUrlSafe(base64Str), ENCODING);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String encodeForUrlSafe(byte[] bytes) {
		Base64 base64 = new Base64(true);
		return base64.encodeToString(bytes);
	}

	public static byte[] decodeForUrlSafe(String base64Str) {
		Base64 base64 = new Base64(true);
		return base64.decode(base64Str);
	}

	// public static void main(String[] args) throws
	// UnsupportedEncodingException {
	// System.out
	// .println(EncodeUtils
	// .urlsafeEncode("sweet:dto/1/0/2014/10/7ab8ea1d3e3f47ac97aa847728873e7c_libx264.mp4"));
	// System.out
	// .println(encode("qiniuphotos:gogopher.jpg".getBytes("utf-8")));
	// }

}
