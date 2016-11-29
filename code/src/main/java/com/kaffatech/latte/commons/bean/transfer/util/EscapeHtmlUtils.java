
package com.kaffatech.latte.commons.bean.transfer.util;


import com.kaffatech.latte.commons.toolkit.base.StringUtils;

/**
 * @author zhen.ling
 *
 */
public class EscapeHtmlUtils {

	public static String escapeHtml(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}

		// "<>&
		String result = value.replaceAll("&", "&amp;").replaceAll(">", "&gt;")
				.replaceAll("<", "&lt;").replaceAll("\"", "&quot;");
		return result;
	}

	public static String reversalHtml(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}

		// "<>&
		String result = value.replaceAll("&amp;", "&").replaceAll("&gt;", ">")
				.replaceAll("&lt;", "<").replaceAll("&quot;", "\"");
		return result;
	}

	public static void main(String[] args) {
		String escape = EscapeHtmlUtils.escapeHtml("<p>sdfds</p>");
		String noescape = EscapeHtmlUtils.reversalHtml(escape);
		System.out.println(escape);
		System.out.println(noescape);
	}
}
