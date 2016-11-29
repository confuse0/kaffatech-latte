
package com.kaffatech.latte.ctx.monitor.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class ContextParameter extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3546455075340682938L;
	
	private int activeSessionNo;

	/**
	 * @return the activeSessionNo
	 */
	public int getActiveSessionNo() {
		return activeSessionNo;
	}

	/**
	 * @param activeSessionNo
	 *            the activeSessionNo to set
	 */
	public void setActiveSessionNo(int activeSessionNo) {
		this.activeSessionNo = activeSessionNo;
	}

}
