
package com.kaffatech.latte.commons.bean.model.paging;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;

/**
 * @author zhen.ling
 *
 */
public enum Order implements TypeBean {

	ASC("ASC", "正序"),

	DESC("DESC", "倒序"); //

	private String code;

	private String name;

	private Order(String code, String name) {
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
