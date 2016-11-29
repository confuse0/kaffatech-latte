
package com.kaffatech.latte.commons.bean.util;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.toolkit.base.EnumUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhen.ling
 * 
 */
public class BeanUtils {

	private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

	static {
		List<String> files = new ArrayList<String>();
		files.add("bean.xml");
		MAPPER.setMappingFiles(files);
	}

	public static String getPropertyString(Object bean, String propName) {
		Object prop = getProperty(bean, propName);
		return (prop == null) ? null : prop.toString();
	}

	public static Object getProperty(Object bean, String propName) {
		try {
			return PropertyUtils.getProperty(bean, propName);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
		try {
			return PropertyUtils.getPropertyDescriptors(clazz);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Enum> T getType(Class<T> clazz, String code) {
		T type = null;
		List<T> enumList = EnumUtils.getEnumList(clazz);
		for (T each : enumList) {
			if (((TypeBean) each).getCode().equals(code)) {
				type = each;
				break;
			}
		}
		return type;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Enum> T getCode(Class<T> clazz, String name) {
		T type = null;
		List<T> enumList = EnumUtils.getEnumList(clazz);
		for (T each : enumList) {
			if (((TypeBean) each).getName().equals(name)) {
				type = each;
				break;
			}
		}
		return type;
	}

	public static void copy(Object orig, Object dest) {
		if (orig == null) {
			return;
		}

		MAPPER.map(orig, dest);
	}

	public static <T> T convert(Object orig, Class<T> clazz) {
		if (orig == null) {
			return null;
		}

		return MAPPER.map(orig, clazz);
	}

	public static <T> List<T> convertList(List<?> orig, Class<T> clazz) {
		List<T> dest = null;

		if (!CollectionUtils.isEmpty(orig)) {
			dest = new ArrayList<T>();
			for (Object each : orig) {
				T destEntry = convert(each, clazz);
				dest.add(destEntry);
			}

		} else if (orig != null) {
			dest = new ArrayList<T>();
		}
		return dest;
	}

	public static <T> T convert(String str, String split, String[] keyArray, Class<T> clazz) {
		String[] strArray = StringUtils.split(str, split);
		Map<String, String> strMap = new HashMap<String, String>();
		int i = 0;
		for (String key : keyArray) {
			strMap.put(key, strArray[i]);
			i++;
		}
		return convert(strMap, clazz);
	}

}
