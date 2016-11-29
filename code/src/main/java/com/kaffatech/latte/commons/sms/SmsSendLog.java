package com.kaffatech.latte.commons.sms;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


public class SmsSendLog extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Long> times = new ArrayList<Long>();

	public SmsSendLog() {
		addTime();
	}

	public void addTime() {
		addTime(System.currentTimeMillis());
	}

	public void addTime(long time) {
		times.add(time);
	}

	public List<Long> getTimes() {
		return times;
	}

	public Long getSubTime(int no) {
		if (times.size() < no) {
			return null;
		}
		List<Long> list = CollectionUtils.sublist(times, times.size() - no);
		return list.get(0);
	}

}
