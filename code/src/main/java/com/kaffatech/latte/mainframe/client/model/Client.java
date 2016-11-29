
package com.kaffatech.latte.mainframe.client.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.mainframe.client.model.type.Channel;

/**
 * @author zhen.ling
 *
 */
public class Client extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3902816645927969686L;

	private String protocolVer;

	private String appVer;

	private Channel channel;

	private String device;

	private String token;

	private String newToken;

	/**
	 * @return the protocolVer
	 */
	public String getProtocolVer() {
		return protocolVer;
	}

	/**
	 * @param protocolVer
	 *            the protocolVer to set
	 */
	public void setProtocolVer(String protocolVer) {
		this.protocolVer = protocolVer;
	}

	/**
	 * @return the appVer
	 */
	public String getAppVer() {
		return appVer;
	}

	/**
	 * @param appVer
	 *            the appVer to set
	 */
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device
	 *            the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the newToken
	 */
	public String getNewToken() {
		return newToken;
	}

	/**
	 * @param newToken
	 *            the newToken to set
	 */
	public void setNewToken(String newToken) {
		this.newToken = newToken;
	}

}
