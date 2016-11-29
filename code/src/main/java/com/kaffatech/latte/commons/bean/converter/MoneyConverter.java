
package com.kaffatech.latte.commons.bean.converter;

import com.kaffatech.latte.commons.toolkit.base.math.MoneyUtils;
import org.dozer.CustomConverter;

import com.kaffatech.latte.commons.bean.model.num.Money;

/**
 * @author zhen.ling
 *
 */
public class MoneyConverter implements CustomConverter {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class destinationClass, Class sourceClass) {
		Object obj = null;
		if (null != sourceFieldValue) {
			if (sourceFieldValue instanceof String) {
				// 字符转数字类型
				obj = MoneyUtils.createMoney((String) sourceFieldValue);
			} else if (sourceFieldValue instanceof Money) {
				// 数字类型转字符(标准型)
				obj = ((Money) sourceFieldValue).toString();
			}
		}
		return obj;
	}

}
