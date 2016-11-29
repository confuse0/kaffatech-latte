
package com.kaffatech.latte.mainframe.dto.type;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class TypeBeanDto extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 值(一般是英文或者拼音名)
	 */
	private String value;

	/**
	 * 是否选中
	 */
	private Boolean selected = false;

	/**
	 * @return the code
	 */
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

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
