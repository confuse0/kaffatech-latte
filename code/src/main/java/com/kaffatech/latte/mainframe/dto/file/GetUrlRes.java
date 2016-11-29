
package com.kaffatech.latte.mainframe.dto.file;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * 
 *
 */
public class GetUrlRes extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 图片路径
	 */
	private String path;

	/**
	 * 图片Url(原始)
	 */
	private String url;

	/**
	 * 图片Url(超小)
	 */
	private String tinyUrl;

	/**
	 * 图片Url(小)
	 */
	private String smallUrl;

	/**
	 * 图片Url(中)
	 */
	private String middleUrl;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the tinyUrl
	 */
	public String getTinyUrl() {
		return tinyUrl;
	}

	/**
	 * @param tinyUrl
	 *            the tinyUrl to set
	 */
	public void setTinyUrl(String tinyUrl) {
		this.tinyUrl = tinyUrl;
	}

	/**
	 * @return the smallUrl
	 */
	public String getSmallUrl() {
		return smallUrl;
	}

	/**
	 * @param smallUrl
	 *            the smallUrl to set
	 */
	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	/**
	 * @return the middleUrl
	 */
	public String getMiddleUrl() {
		return middleUrl;
	}

	/**
	 * @param middleUrl
	 *            the middleUrl to set
	 */
	public void setMiddleUrl(String middleUrl) {
		this.middleUrl = middleUrl;
	}

}