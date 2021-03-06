
package com.kaffatech.latte.commons.bean.model.type;

/**
 * @author zhen.ling
 *
 */
public enum DelFlag implements TypeBean {

	NOT_DELETED("1", "未删除"), DELETED("0", "已删除");

	private String code;

	private String name;

	private DelFlag(String code, String name) {
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