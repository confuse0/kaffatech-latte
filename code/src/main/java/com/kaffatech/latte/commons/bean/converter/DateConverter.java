
package com.kaffatech.latte.commons.bean.converter;

import com.kaffatech.latte.commons.bean.model.date.Time;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;
import org.dozer.CustomConverter;

import java.util.Date;

/**
 * @author zhen.ling
 *
 */
public class DateConverter implements CustomConverter {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class destinationClass, Class sourceClass) {
		Object obj = null;
		if (null != sourceFieldValue) {
			if (sourceFieldValue instanceof String) {
				// 字符转日期类型（秒）
				obj = DateUtils.parseToDate((String) sourceFieldValue);
			} else if (sourceFieldValue instanceof Time) {
				// 日期类型转字符（秒）
				obj = DateUtils.format((Date) sourceFieldValue);
			}
		}
		return obj;
	}

}
