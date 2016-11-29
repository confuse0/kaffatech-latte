package com.kaffatech.latte.mainframe.dto;

public class LoginErrorRes extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loginUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}
