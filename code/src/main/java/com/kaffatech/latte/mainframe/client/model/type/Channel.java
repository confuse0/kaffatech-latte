
package com.kaffatech.latte.mainframe.client.model.type;

/**
 * @author zhen.ling
 *
 */
public enum Channel {

	PC("电脑"), ANDROID("安卓"), IOS("苹果");

	private String name;

	private Channel(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
