
package com.kaffatech.latte.commons.bean.converter;

import org.dozer.CustomConverter;

import com.kaffatech.latte.commons.toolkit.base.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @author zhen.ling
 *
 */
public class BigDecimalConverter implements CustomConverter {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class destinationClass, Class sourceClass) {
		Object obj = null;
		if (null != sourceFieldValue) {
			if (sourceFieldValue instanceof String) {
				// 字符转数字类型
				obj = NumberUtils.createBigDecimal((String) sourceFieldValue);
			} else if (sourceFieldValue instanceof BigDecimal) {
				// 数字类型转字符(标准型)
				obj = NumberUtils.decimalToString((BigDecimal) sourceFieldValue);
			}
		}
		return obj;
	}

}
