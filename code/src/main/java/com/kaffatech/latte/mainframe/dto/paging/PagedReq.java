
package com.kaffatech.latte.mainframe.dto.paging;

/**
 * @author zhen.ling
 *
 */
public class PagedReq<T> extends BasePagedReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8970062319769618446L;

	private T condition;

	/**
	 * @return the condition
	 */
	public T getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 *            the condition to set
	 */
	public void setCondition(T condition) {
		this.condition = condition;
	}

}
