
package com.kaffatech.latte.test;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhen.ling
 *
 */
public class DataApplier {

	public static <T> T fill(Class<T> clazz) {
		T obj;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		fillData(obj);
		return obj;
	}

	public static void fillData(Object obj) {
		try {
			if (obj != null) {
				PropertyDescriptor[] props = PropertyUtils
						.getPropertyDescriptors(obj.getClass());

				for (PropertyDescriptor prop : props) {
					Method readMethod = prop.getReadMethod();
					Class<?> propType = readMethod.getReturnType();
					if (String.class.isAssignableFrom(propType)) {
						// 字符串
						prop.getWriteMethod().invoke(obj, "你好中国hahaha");
					} else if (Integer.class.isAssignableFrom(propType)) {
						// 整型
						prop.getWriteMethod().invoke(obj, 12321);

					} else if (Long.class.isAssignableFrom(propType)) {
						// 长整型
						prop.getWriteMethod().invoke(obj, 9129382190L);

					} else if (List.class.isAssignableFrom(propType)) {
						// List类型
						List<?> propValue = (List<?>) readMethod.invoke(obj);
						if (propValue != null) {
							for (Object each : propValue) {
								fillData(each);
							}
						}
					} else if (Set.class.isAssignableFrom(propType)) {
						// Set类型
						Set<?> propValue = (Set<?>) readMethod.invoke(obj);
						if (propValue != null) {
							for (Object each : propValue) {
								fillData(each);
							}
						}
					} else if (Map.class.isAssignableFrom(propType)) {
						// Map类型暂时不支持转义
					} else if (BaseBean.class.isAssignableFrom(propType)) {
						// 复合类型
						Object propValue = readMethod.invoke(obj);
						if (propValue != null) {
							fillData(propValue);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
