
package com.kaffatech.latte.commons.bean.converter;

import com.kaffatech.latte.commons.bean.model.num.Money;
import org.dozer.CustomConverter;

/**
 * @author zhen.ling
 *
 */
public class MoneyMoneyConverter implements CustomConverter {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class destinationClass, Class sourceClass) {
		Object obj = null;
		if (null != sourceFieldValue) {
			obj = new Money(((Money) sourceFieldValue).getValue());
		}
		return obj;
	}

}
