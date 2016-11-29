
package com.kaffatech.latte.commons.bean.model.type;

/**
 * @author zhen.ling
 *
 */
public enum Sex implements TypeBean {

	MALE("1", "男"), FEMALE("2", "女");

	private String code;

	private String name;

	private Sex(String code, String name) {
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