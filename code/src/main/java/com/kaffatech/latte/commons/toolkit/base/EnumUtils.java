
package com.kaffatech.latte.commons.toolkit.base;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.bean.util.BeanUtils;

/**
 * @author zhen.ling
 * @param <T>
 *
 */
public abstract class EnumUtils extends org.apache.commons.lang3.EnumUtils {
	
	@SuppressWarnings("rawtypes")
	public static <T extends Enum> String getEnumName(Class<T> clazz, String code){
		String name = ((TypeBean) BeanUtils.getType(clazz, code)).getName();
		return name;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends Enum> String getEnumCode(Class<T> clazz, String name){
		String code = ((TypeBean) BeanUtils.getCode(clazz, name)).getCode();
		return code;
	}
}
