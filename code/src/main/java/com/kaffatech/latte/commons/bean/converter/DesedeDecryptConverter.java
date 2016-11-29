
package com.kaffatech.latte.commons.bean.converter;

import com.kaffatech.latte.security.util.KeyUtils;
import com.kaffatech.latte.security.util.DesedeUtils;
import org.dozer.CustomConverter;

/**
 * @author zhen.ling
 *
 */
public class DesedeDecryptConverter implements CustomConverter {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class destinationClass, Class sourceClass) {
		Object obj = null;
		if (null != sourceFieldValue) {
			if (sourceFieldValue instanceof String) {
				obj = DesedeUtils.decrypt(KeyUtils.getCryptKey(), (String) sourceFieldValue);
			}
		}
		return obj;
	}

}
