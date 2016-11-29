
package com.kaffatech.latte.commons.bean.model.type;

/**
 * @author zhen.ling
 *
 */
public enum BooleanType implements TypeBean {

	YES("1", "是"),
	NO("0", "否");

	private String code;

	private String name;

	BooleanType(String code, String name) {
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