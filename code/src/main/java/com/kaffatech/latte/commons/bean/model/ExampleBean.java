
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
	 * GroupBy子句
	 */
	private String groupByClause;

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

	public String getGroupByClause() {
		return groupByClause;
	}

	public void setGroupByClause(String groupByClause) {
		this.groupByClause = groupByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

}
