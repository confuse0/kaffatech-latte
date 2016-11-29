
package com.kaffatech.latte.ctx.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhen.ling
 *
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext CTX = null;

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.CTX = applicationContext;
	}

	public static Object getBean(String name) {
		// CTX = ContextLoader.getCurrentWebApplicationContext();
		return CTX.getBean(name);
	}
}
