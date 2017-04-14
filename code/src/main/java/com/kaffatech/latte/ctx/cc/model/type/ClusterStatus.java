
package com.kaffatech.latte.ctx.cc.model.type;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;

/**
 * @author zhen.ling
 *
 */
public enum ClusterStatus implements TypeBean {

	RUNNING("00", "运行中"),
	IN_SERVICE("01", "服务中"),
	STOPED("10", "停止"),
	CLOSED("99", "关闭");

	private String code;

	private String name;

	ClusterStatus(String code, String name) {
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