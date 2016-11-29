
package com.kaffatech.latte.ctx.base.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhen.ling
 *
 */
public class RequestContextHolder {

	public static final String CLIENT_TOKEN_KEY = "client_token";

	public static boolean fromMySite() {
		String ts = getParameter("_");
		if (!StringUtils.isEmpty(ts) && Math.abs(System.currentTimeMillis() - Long.parseLong(ts)) > 10800000L) {
			// 如果时间差距在3小时以上则代表无效
			return false;
		}

		if ((!StringUtils.contains(getReferUrl(), SystemProperties.getDomain())) || StringUtils.isEmpty(getClientToken())) {
			return false;
		}
		return true;
	}

	public static HttpServletRequest getRequest() {
		RequestAttributes ra = org.springframework.web.context.request.RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();

		return request;
	}

	public static String getParameter(String name) {
		HttpServletRequest request = getRequest();
		if (request != null) {
			return request.getParameter(name);
		}

		return null;
	}

	public static String getHeader(String name) {
		HttpServletRequest request = getRequest();
		if (request != null) {
			return request.getHeader(name);
		}

		return null;
	}

	public static Map<String, String> getHeaderMap() {
		Map<String, String> headerMap = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		if (request != null) {
			Enumeration<String> names = request.getHeaderNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				headerMap.put(name, request.getHeader(name));
			}

		}

		return headerMap;
	}

	public static String getRemoteAddr() {
		HttpServletRequest request = getRequest();
		if (request != null) {
			return request.getRemoteAddr();
		}

		return null;
	}

	public static String getUrl() {
		HttpServletRequest request = getRequest();

		if (request != null) {
			String orgUrl = request.getHeader("request_url");
			if (!StringUtils.isEmpty(orgUrl)) {
				// 请求头还有原始URL信息
				return orgUrl;
			}

			StringBuffer url = request.getRequestURL();
			if (!StringUtils.isEmpty(request.getQueryString())) {
				url.append('?');
				url.append(request.getQueryString());
			}
			return url.toString();
		}

		return null;
	}

	public static String getReferUrl() {
		HttpServletRequest request = getRequest();

		if (request != null) {
			String referUrl = request.getHeader("Referer");
			return referUrl;
		}

		return null;
	}

	public static String getUserAgent() {
		return getHeader("User-Agent");
	}

	public static String getCookie(String name) {
		HttpServletRequest request = getRequest();

		Cookie[] cookies = request.getCookies();
		String value = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (!StringUtils.isEmpty(cookie.getName())
						&& StringUtils.equals(name, cookie.getName())) {
					value = cookie.getValue();
					break;
				}
			}
		}

		return value;
	}

	public static String getClientToken() {
		return getCookie(CLIENT_TOKEN_KEY);
	}

	public static Map<String, Object> getParamMap() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HttpServletRequest req = getRequest();
		if (req == null) {
			return paramMap;
		}

		Enumeration<String> names = req.getParameterNames();

		while (names.hasMoreElements()) {
			String name = names.nextElement();
			Object value = req.getParameter(name);

			paramMap.put(name, value);
		}

		return paramMap;
	}

}
