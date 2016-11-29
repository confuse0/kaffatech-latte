
package com.kaffatech.latte.commons.bean.util;

import java.util.List;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.bean.model.type.Currency;
import com.kaffatech.latte.commons.toolkit.base.EnumUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

/**
 * @author zhen.ling
 *
 */
public class TypeBeanUtils {

	public static <T> T getType(Class<? extends TypeBean> clazz, String code) {
		T obj = null;
		List enumList = EnumUtils.getEnumList((Class<? extends Enum>) clazz);
		for (Object each : enumList) {
			TypeBean type = (TypeBean) each;
			if (type.getCode().equals(code)) {
				obj = (T) each;
				break;
			}
		}

		return obj;
	}

	public static boolean equals(TypeBean type1, TypeBean type2) {
		if (type1 == type2) {
			return true;
		}
		if (type1 == null || type2 == null) {
			return false;
		}

		return StringUtils.equals(type1.getCode(), type2.getCode());
	}

	public static void main(String[] args) {
		System.out.println(TypeBeanUtils.getType(Currency.class, "CNY"));
	}

}
