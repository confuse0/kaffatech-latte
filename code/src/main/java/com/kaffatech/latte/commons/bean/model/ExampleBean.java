
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
	 * Distinct子句
	 */
	private String distinctClause;

	/**
	 * OrderBy子句
	 */
	private String orderByClause;

	public String getDistinctClause() {
		return distinctClause;
	}

	public void setDistinctClause(String distinctClause) {
		this.distinctClause = distinctClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

}
