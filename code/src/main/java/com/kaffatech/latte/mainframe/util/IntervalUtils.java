
package com.kaffatech.latte.mainframe.util;

import com.kaffatech.latte.commons.bean.model.type.Interval;
import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.toolkit.base.EnumUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author zhen.ling
 *
 */
public abstract class IntervalUtils {

	public static String merge(String mutiIndex, int num,
			Class<? extends Enum> clazz) {
		Set<String> set = new TreeSet<String>();
		if (StringUtils.isNotEmpty(mutiIndex)) {
			String[] array = StringUtils.split(mutiIndex, StringUtils.SEP);
			for (String each : array) {
				set.add(each);
			}
		}
		set.addAll(calculateSet(num, clazz));

		return StringUtils.concatBySepAndColl(StringUtils.SEP, set);
	}

	private static Set<String> calculateSet(int num, Class<? extends Enum> clazz) {
		Set<String> set = new TreeSet<String>();

		List<Enum> enumList = (List<Enum>) EnumUtils.getEnumList(clazz);
		for (Enum each : enumList) {
			int min = ((Interval) each).getMin();
			int max = ((Interval) each).getMax();

			if (num >= min && num <= max) {
				set.add(((TypeBean) each).getCode());
			}
		}

		return set;
	}
}