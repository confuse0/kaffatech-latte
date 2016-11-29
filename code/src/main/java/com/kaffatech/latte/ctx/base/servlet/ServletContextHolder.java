
package com.kaffatech.latte.ctx.base.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zhen.ling
 *
 */
public class ServletContextHolder {

	private static ThreadLocal<HttpServletRequest> REQEUST = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<ServletContext> APPLICATOIN = new ThreadLocal<ServletContext>();

	public static void setRequestCtx(HttpServletRequest req) {
		REQEUST.set(req);
	}

	public static HttpServletRequest getRequestCtx() {
		return REQEUST.get();
	}

	public static HttpSession getSessionCtx() {
		return getSessionCtx(true);
	}

	public static HttpSession getSessionCtx(boolean create) {
		HttpServletRequest req = getRequestCtx();
		if (req == null) {
			return null;
		}
		return req.getSession(create);
	}

	public static void setApplicationCtx(ServletContext applicationCtx) {
		APPLICATOIN.set(applicationCtx);
	}

	public static ServletContext getApplicationCtx() {
		return APPLICATOIN.get();
	}

	public static void clearRequest() {
		REQEUST.remove();
	}

	public static void clear() {
		REQEUST.remove();
		APPLICATOIN.remove();
	}

}
