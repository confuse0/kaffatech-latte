
package com.kaffatech.latte.commons.toolkit.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhen.ling
 *
 */
public class RegexUtils {

	public static boolean match(String value, String regex) {
		return value.matches(regex);
	}

	public static String group(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(match("s1asc", "s\\d+(asc|desc)"));
		System.out.println(group("s12", "\\d+"));
	}

}
