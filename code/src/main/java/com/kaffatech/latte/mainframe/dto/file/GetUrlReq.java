
package com.kaffatech.latte.mainframe.dto.file;

import com.kaffatech.latte.mainframe.dto.Response;

/**
 * 
 *
 */
public class GetUrlReq extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String path;

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

}