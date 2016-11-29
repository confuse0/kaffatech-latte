
package com.kaffatech.latte.commons.bean.converter;

import java.util.List;

import org.dozer.CustomConverter;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.toolkit.base.EnumUtils;

/**
 * @author zhen.ling
 *
 */
public class TypeBeanConverter implements CustomConverter {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class destinationClass, Class sourceClass) {
		Object obj = null;
		if (null != sourceFieldValue) {
			if (sourceFieldValue instanceof String) {
				// 整型转枚举
				List enumList = EnumUtils.getEnumList(destinationClass);
				for (Object each : enumList) {
					TypeBean type = (TypeBean) each;
					if (((String) sourceFieldValue).equals(type.getCode())) {
						obj = each;
						break;
					}
				}
			} else if (sourceFieldValue instanceof TypeBean) {
				// 枚举转整型
				obj = ((TypeBean) sourceFieldValue).getCode();
			}
		}
		return obj;
	}

}
