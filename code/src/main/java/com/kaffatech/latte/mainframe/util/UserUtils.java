
package com.kaffatech.latte.mainframe.util;

import com.kaffatech.latte.commons.bean.model.IdBean;
import com.kaffatech.latte.ctx.base.servlet.SessionContextHolder;
import com.kaffatech.latte.ctx.base.SystemProperties;

/**
 * @author zhen.ling
 *
 */
public class UserUtils {

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public static <T> T getUser(Class<T> clazz, String key){
		return (T) SessionContextHolder.get(key);
	}

	public static Long getUserId(String key) {
		IdBean idBean = (IdBean) SessionContextHolder.get(key);
		Long uid = (idBean == null) ? null : idBean.getId();
		if (uid == null) {
			if (SystemProperties.isUnitTest()) {
				uid = 123456789L;
			}
		}
		return uid;
	}
}
