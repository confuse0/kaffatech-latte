
package com.kaffatech.latte.commons.net.http.util;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.commons.toolkit.codec.EncodingUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhen.ling
 *
 */
public class UrlEncodingUtils {

	public static List<String> CHAR_LIST = new ArrayList<String>();
	public static List<String> REVERSE_CHAR_LIST = new ArrayList<String>();

	static {
		CHAR_LIST.add("%:%25");
		CHAR_LIST.add("/:%2F");
		CHAR_LIST.add("&:%26");
		CHAR_LIST.add("?:%3F");
		CHAR_LIST.add("+:%2B");
		CHAR_LIST.add(" :+");// %20
		CHAR_LIST.add("#:%23");
		CHAR_LIST.add("=:%3D");

		for (int i = CHAR_LIST.size() - 1; i >= 0; i--) {
			REVERSE_CHAR_LIST.add(CHAR_LIST.get(i));
		}
	}

	public static String strictEncode(String urlStr) {
		if (StringUtils.isEmpty(urlStr)) {
			return urlStr;
		}

		try {
			return URLEncoder.encode(urlStr, EncodingUtils.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String encode(String urlStr) {
		if (StringUtils.isEmpty(urlStr)) {
			return urlStr;
		}

		String encodedStr = urlStr;
		for (String each : CHAR_LIST) {
			String[] array = each.split(":");
			encodedStr = encodedStr.replace(array[0], array[1]);
		}

		return encodedStr;
	}

	public static String strictDecode(String urlStr) {
		if (StringUtils.isEmpty(urlStr)) {
			return urlStr;
		}

		try {
			return URLDecoder.decode(urlStr, EncodingUtils.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String decode(String urlStr) {
		if (StringUtils.isEmpty(urlStr)) {
			return urlStr;
		}

		String encodedStr = urlStr;
		for (String each : REVERSE_CHAR_LIST) {
			String[] array = each.split(":");
			encodedStr = encodedStr.replace(array[1], array[0]);
		}

		return encodedStr;
	}

	public static void main(String[] args) {
		System.out.println(encode("我%/&?+ #=A"));
		System.out.println(decode(encode("我%/&?+ #=A")));
	}
}
