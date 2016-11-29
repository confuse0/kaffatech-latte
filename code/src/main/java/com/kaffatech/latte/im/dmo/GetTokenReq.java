
package com.kaffatech.latte.im.dmo;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class GetTokenReq extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7938135811963437649L;

	private String grant_type = "client_credentials";

	private String client_id = "YXA6ez1qoBVaEeSxSauRYqI8QQ";

	private String client_secret = "YXA6Dve0iJ07VNyPNxAxSALutG6zj1g";

	/**
	 * @return the grant_type
	 */
	public String getGrant_type() {
		return grant_type;
	}

	/**
	 * @param grant_type
	 *            the grant_type to set
	 */
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}

	/**
	 * @param client_id
	 *            the client_id to set
	 */
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	/**
	 * @return the client_secret
	 */
	public String getClient_secret() {
		return client_secret;
	}

	/**
	 * @param client_secret
	 *            the client_secret to set
	 */
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

}
