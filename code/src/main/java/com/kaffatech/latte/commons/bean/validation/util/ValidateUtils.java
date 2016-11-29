package com.kaffatech.latte.commons.bean.validation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	public static boolean validateMobile(String mobile) {
		Pattern p = Pattern.compile("^[1|8][3|4|5|7|8][0-9]\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.find();
	}

	public static boolean validateEmail(String email) {
		Pattern p = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher m = p.matcher(email);
		return m.find();
	}

	public static void main(String[] args) {
		System.out.println(validateEmail("13717xxx708@163.com"));
	}

}
