
package com.kaffatech.latte.commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhen.ling
 *
 */
public class Log {

	public static final Logger WARN_LOGGER = LoggerFactory
			.getLogger("WARN-INFO");

	public static final Logger ERROR_LOGGER = LoggerFactory
			.getLogger("ERROR-INFO");

	/**
	 * 通用调用日志
	 */
	public static final Logger CTRL_DIGEST_LOGGER = LoggerFactory
			.getLogger("CTRL-DIGEST");

	/**
	 * 任务日志
	 */
	public static final Logger SCHEDULING_LOGGER = LoggerFactory
			.getLogger("SCHEDULING");

	/**
	 * 短信发送日志
	 */
	public static final Logger SMS_DIGEST_LOGGER = LoggerFactory
			.getLogger("SMS-DIGEST");

	/**
	 * 微服务日志
	 */
	public static final Logger SERVICE_DIGEST_LOGGER = LoggerFactory
			.getLogger("SERVICE-DIGEST");

}
