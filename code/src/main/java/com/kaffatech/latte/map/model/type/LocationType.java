package com.kaffatech.latte.map.model.type;

public enum LocationType {

	GCJ02("国测局加密经纬度坐标"), BD09LL("百度加密经纬度坐标");

	private String name;

	private LocationType(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return ordinal();
	}
}
