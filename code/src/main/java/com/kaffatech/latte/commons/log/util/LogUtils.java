
package com.kaffatech.latte.commons.log.util;

import org.slf4j.Logger;

/**
 * @author zhen.ling
 *
 */
public class LogUtils {

	public static void debug(Logger logger, Object... msg) {
		if (logger.isDebugEnabled()) {
			logger.debug(concatMsg(msg));
		}
	}

	public static void debug(Logger logger, Throwable t, Object... msg) {
		if (logger.isDebugEnabled()) {
			String extMsg = "";
			if (msg != null) {
				extMsg = " (" + concatMsg(msg) + ")";
			}
			logger.debug(t.getMessage() + extMsg, t);
		}
	}

	public static void info(Logger logger, Object... msg) {
		if (logger.isInfoEnabled()) {
			logger.info(concatMsg(msg));
		}
	}

	public static void info(Logger logger, Throwable t, Object... msg) {
		if (logger.isInfoEnabled()) {
			String extMsg = "";
			if (msg != null) {
				extMsg = " (" + concatMsg(msg) + ")";
			}
			logger.info(t.getMessage() + extMsg, t);
		}
	}

	public static void warn(Logger logger, Object... msg) {
		if (logger.isWarnEnabled()) {
			logger.warn(concatMsg(msg));
		}
	}

	public static void warn(Logger logger, Throwable t, Object... msg) {
		if (logger.isWarnEnabled()) {
			String extMsg = "";
			if (msg != null) {
				extMsg = " (" + concatMsg(msg) + ")";
			}
			logger.warn(t.getMessage() + extMsg, t);
		}
	}

	public static void error(Logger logger, Object... msg) {
		if (logger.isErrorEnabled()) {
			logger.error(concatMsg(msg));
		}
	}

	public static void error(Logger logger, Throwable t, Object... msg) {
		if (logger.isErrorEnabled()) {
			String extMsg = "";
			if (msg != null) {
				extMsg = " (" + concatMsg(msg) + ")";
			}
			logger.error(t.getMessage() + extMsg, t);
		}
	}

	private static String concatMsg(Object... msg) {
		StringBuilder sb = new StringBuilder();
		for (Object each : msg) {
			sb.append(each);
		}
		return sb.toString();
	}


}
