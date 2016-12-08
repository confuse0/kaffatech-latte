package com.kaffatech.latte.commons.toolkit.base;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author zhen.ling
 *
 */
public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static final String SEP = " ";
	public static final String TEXT_SEP = " ";
	public static final String uicKV_SEP = ":";

	public static final String LINE_SEP = System.getProperty("line.separator");

	public static final String FILE_SEP = System.getProperty("file.separator");

	public static final String HTML_LINE_SEP = "<br/>";

	public static String upperFc(String str) {
		if (isEmpty(str)) {
			return str;
		}
		if (str.length() == 1) {
			return str.toUpperCase();
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String concat(Object... obj) {
		StringBuilder sb = new StringBuilder();
		for (Object each : obj) {
			sb.append(each);
		}
		return sb.toString();
	}

	public static String concatBySepAndColl(String sep, Collection<?> collection) {
		Iterator<?> iter = collection.iterator();
		int size = collection.size();
		int i = 0;
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			Object item = iter.next();
			sb.append(item);
			if (i + 1 < size) {
				sb.append(sep);
			}
			i++;
		}
		return sb.toString();
	}

	public static boolean isEmpty(String txt) {
		if ("".equals(txt) || txt == null) {
			return true;
		}
		return false;
	}

	public static String chop(String str, int lastLen) {
		return str.substring(0, str.length() - lastLen);
	}

	public static void main(String[] args) {
		System.out.println(chop("abc", 1));
	}
}
