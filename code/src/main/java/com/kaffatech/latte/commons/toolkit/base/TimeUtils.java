
package com.kaffatech.latte.commons.toolkit.base;

import com.kaffatech.latte.commons.bean.model.date.Time;

/**
 * @author zhen.ling
 *
 */
public class TimeUtils {

	public static Time parseToTime(String value) {
		Time time = new Time(value);
		return time;
	}

	public static String format(Time time) {
		if (time == null) {
			return null;
		}
		return time.toString();
	}

	public static String formatShort(Time time) {
		if (time == null) {
			return null;
		}
		return time.toShortString();
	}

}
