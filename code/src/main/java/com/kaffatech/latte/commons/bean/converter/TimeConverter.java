
package com.kaffatech.latte.commons.bean.converter;

import com.kaffatech.latte.commons.bean.model.date.Time;
import com.kaffatech.latte.commons.toolkit.base.TimeUtils;
import org.dozer.CustomConverter;

/**
 * @author zhen.ling
 *
 */
public class TimeConverter implements CustomConverter {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class destinationClass, Class sourceClass) {
		Object obj = null;
		if (null != sourceFieldValue) {
			if (sourceFieldValue instanceof String) {
				// 字符转时间类型
				obj = TimeUtils.parseToTime((String) sourceFieldValue);
			} else if (sourceFieldValue instanceof Time) {
				// 时间类型转字符(标准型)
				obj = TimeUtils.format((Time) sourceFieldValue);
			}
		}
		return obj;
	}

}
