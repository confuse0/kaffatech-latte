package com.kaffatech.latte.ctx.base.servlet.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;

public class GeneralListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String webServerRoot = cat(event.getServletContext().getRealPath("/"),
				2);

		System.setProperty("webServerRoot", webServerRoot);

		super.contextInitialized(event);
	}

	private String cat(String url, int level) {
		String catUrl = StringUtils.removeEnd(url, "/");
		for (int i = 0; i < level; i++) {
			String[] urlArray = catUrl.split("/");
			String tail = urlArray[urlArray.length - 1];
			catUrl = StringUtils.removeEnd(
					catUrl.substring(0, catUrl.length() - tail.length()), "/");
		}
		return catUrl;

	}
}
