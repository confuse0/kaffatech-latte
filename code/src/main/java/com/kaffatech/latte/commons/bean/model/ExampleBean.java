
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
	private Long orderByClause;

	public Long getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(Long orderByClause) {
		this.orderByClause = orderByClause;
	}
}
