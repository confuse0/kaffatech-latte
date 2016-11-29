
package com.kaffatech.latte.map.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class Address extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 地址名
	 */
	private String name;

	/**
	 * 位置
	 */
	private Location location;

	/**
	 * 详细地址
	 */
	private String detail;

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
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

}
