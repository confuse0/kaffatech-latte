package com.kaffatech.latte.commons.net.http.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtils {

	private static final String ENC = "UTF-8";

	public static String encode(String s) {
		try {
			return URLEncoder.encode(s, ENC);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

}
