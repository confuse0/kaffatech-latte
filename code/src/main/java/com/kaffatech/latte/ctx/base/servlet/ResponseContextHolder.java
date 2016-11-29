
package com.kaffatech.latte.ctx.base.servlet;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhen.ling
 *
 */
public class ResponseContextHolder {

	private static ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<HttpServletResponse>();

	public static HttpServletResponse getResponse() {
		return RESPONSE.get();
	}

	public static void setResponse(HttpServletResponse res) {
		RESPONSE.set(res);
	}

	public static void clear() {
		RESPONSE.remove();
	}

}
