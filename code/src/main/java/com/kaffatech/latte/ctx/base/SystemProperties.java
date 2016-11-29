
package com.kaffatech.latte.ctx.base;

import com.kaffatech.latte.commons.toolkit.base.BooleanUtils;
import com.kaffatech.latte.security.util.DesedeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhen.ling
 *
 */
public class SystemProperties extends PropertyPlaceholderConfigurer {

	private static final String KEY_OF_KEY_NAME = "keyOfKey";

	private static final String DECRYPT_POSTFIX = "-decrypt";

	private static Properties properties = new Properties();

	@SuppressWarnings("rawtypes")
	public void init() throws IOException {
		properties = mergeProperties();
	}

	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);

		// 设置KeyOfKey
		String keyOfKey = "3MSw/tnQzu9RRrODZJhbrrB/WHl5+Kei";
		props.setProperty(KEY_OF_KEY_NAME, keyOfKey);
		System.setProperty(KEY_OF_KEY_NAME, keyOfKey);

		// 同步一份到系统变量中去
		Iterator it = properties.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			if (BooleanUtils.toBoolean(properties.getProperty(key + DECRYPT_POSTFIX))) {
				// 需要解密
				value = DesedeUtils.decrypt(System.getProperty(KEY_OF_KEY_NAME), value);
				props.setProperty(key, value);
			}
			System.setProperty(key, value);
		}
	}

	public static String getKeyOfKey() {
		return System.getProperty(KEY_OF_KEY_NAME);
	}

	public static String getProperty(String key) {
		String property = properties.getProperty(key);
		if (StringUtils.isEmpty(property)) {
			// 如果自己的环境变量没有找到则再去系统环境变量里找
			property = System.getProperty(key);
		}
		return property;
	}

	public static long getShardingId() {
		String sidStr = getProperty("shardingId");
		long sid = 0L;
		if (!StringUtils.isEmpty(sidStr)) {
			sid = Long.parseLong(sidStr);
		}
		return sid;
	}

	public static String getDomain() {
		return getProperty("domain");
	}

	public static boolean isProduction() {
		if (StringUtils.equals("production", getProperty("env"))) {
			return true;
		}
		return false;
	}

	public static boolean isUnitTest() {
		if (isProduction()) {
			return false;
		}

		if (StringUtils.equals("true", getProperty("unitTest"))) {
			return true;
		}
		return false;
	}
}
