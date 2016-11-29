
package com.kaffatech.latte.ctx.base.servlet;

import com.kaffatech.latte.ctx.base.SystemProperties;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zhen.ling
 *
 */
public class SessionContextHolder {

	/**
	 * 当前URL
	 */
	public static final String URL_KEY = "url";

	/**
	 * 上一个URl
	 */
	public static final String LAST_URL_KEY = "lastUrl";
	/**
	 * 登录成功后跳回的URL(非AJAX请求)
	 */
	public static final String LAST_LOGIN_REDIRECT_KEY = "lastLoginRedirectUrl";

	private static HttpSession MOCK_SESSION = new MockHttpSession();

	@SuppressWarnings("unchecked")
	public static <T> T get(String key, Class<T> clazz) {
		return (T) get(key);
	}

	public static Object get(String key) {
		return getSession().getAttribute(key);
	}

	public static void put(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void remove(String key) {
		getSession().removeAttribute(key);
	}

	public static HttpSession getSession() {
		HttpSession session = null;

		if (SystemProperties.isUnitTest()) {
			session = MOCK_SESSION;
		} else {
			RequestAttributes ra = org.springframework.web.context.request.RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = ((ServletRequestAttributes) ra)
					.getRequest();
			session = request.getSession();
		}

		return session;
	}

}
