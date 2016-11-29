
package com.kaffatech.latte.mainframe.dto.file;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class ImgDto extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否是封面
	 */
	private String isFirst;

	/**
	 * 图片路径
	 */
	private String path;

	/**
	 * 图片Url(原始)
	 */
	private String url;

	/**
	 * @return the isFirst
	 */
	public String getIsFirst() {
		return isFirst;
	}

	/**
	 * @param isFirst
	 *            the isFirst to set
	 */
	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}

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

}
