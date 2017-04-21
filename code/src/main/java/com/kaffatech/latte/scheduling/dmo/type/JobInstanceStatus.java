
package com.kaffatech.latte.scheduling.dmo.type;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;

/**
 * @author zhen.ling
 *
 */
public enum JobInstanceStatus implements TypeBean {

	PROCESSING("00", "处理中"),
	SUCCESS("10", "成功"),
	FAILED("20", "失败"),
	PENDING("90", "未知挂起");

	private String code;

	private String name;

	JobInstanceStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * @return the code
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}