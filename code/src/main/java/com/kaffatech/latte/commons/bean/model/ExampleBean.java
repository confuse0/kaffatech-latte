
package com.kaffatech.latte.commons.bean.model;

/**
 * @author zhen.ling
 *
 */
public class ExampleBean extends BaseBean {

	/**
	 * 版本序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * OrderBy子句
	 */
	private String orderByClause;

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
}
