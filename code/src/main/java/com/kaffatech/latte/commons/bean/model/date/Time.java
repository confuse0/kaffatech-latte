
package com.kaffatech.latte.commons.bean.model.date;

import org.apache.commons.lang.StringUtils;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class Time extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String TIME_SEP = ":";

	private static String SECOND_TIME_FMT = "HH:mm:ss";
	private static String MINUTE_TIME_FMT = "HH:mm";
	private static String HOUR_TIME_FMT = "HH";

	private static String SHORT_SECOND_TIME_FMT = "HHmmss";
	private static String SHORT_MINUTE_TIME_FMT = "HHmm";
	private static String SHORT_HOUR_TIME_FMT = "HH";

	/**
	 * 时间类型
	 */
	private TimeType type;

	/**
	 * 时间值(短类型)
	 */
	private String value;

	public Time(String value) {
		String trimValue = StringUtils.trim(value);
		if (StringUtils.contains(trimValue, TIME_SEP)) {
			initFmt(trimValue);
		} else {
			initShortFmt(trimValue);
		}
	}

	public String toString() {
		String fmtStr = null;

		switch (type) {
		case SECOND:
			fmtStr = StringUtils.substring(value, 0, 2) + TIME_SEP
					+ StringUtils.substring(value, 2, 4) + TIME_SEP
					+ StringUtils.substring(value, 4, 6);
			break;
		case MINUTE:
			fmtStr = StringUtils.substring(value, 0, 2) + TIME_SEP
					+ StringUtils.substring(value, 2, 4);
			break;
		case HOUR:
			fmtStr = StringUtils.substring(value, 0, 2);
			break;
		}

		return fmtStr;
	}

	public String toShortString() {
		return getValue();
	}

	private String getValue() {
		return value;
	}

	/**
	 * 非精简格式
	 * 
	 * @param value
	 */
	private void initFmt(String value) {
		if (value.length() == SECOND_TIME_FMT.length()) {
			type = TimeType.SECOND;
			this.value = StringUtils.remove(value, TIME_SEP);
		} else if (value.length() == MINUTE_TIME_FMT.length()) {
			type = TimeType.MINUTE;
			this.value = StringUtils.remove(value, TIME_SEP);
		} else if (value.length() == HOUR_TIME_FMT.length()) {
			type = TimeType.HOUR;
			this.value = StringUtils.remove(value, TIME_SEP);
		}
	}

	/**
	 * 初始化精简格式
	 * 
	 * @param value
	 */
	private void initShortFmt(String value) {
		if (value.length() == SHORT_SECOND_TIME_FMT.length()) {
			type = TimeType.SECOND;
			this.value = value;
		} else if (value.length() == SHORT_MINUTE_TIME_FMT.length()) {
			type = TimeType.MINUTE;
			this.value = value;
		} else if (value.length() == SHORT_HOUR_TIME_FMT.length()) {
			type = TimeType.HOUR;
			this.value = value;
		}
	}

	public boolean equals(Time obj) {
		return value.equals(obj.getValue());
	}

	public int hashCode() {
		return value.hashCode();
	}

	public static void main(String[] args) {
		Time dt = new Time("03:04:05");
		System.out.println(dt);
		System.out.println(dt.toShortString());
	}

}
