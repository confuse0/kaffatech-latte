
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
	 * 是否DISTINCT
	 */
	private Boolean distinct;

	/**
	 * OrderBy子句
	 */
	private String orderByClause;

	/**
	 * 起始行号
	 */
	private Integer start;

	/**
	 * 单页行数
	 */
	private Integer rows;

	public Boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
