
package com.kaffatech.latte.commons.bean.model.paging;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.commons.bean.model.ExampleBean;
import com.kaffatech.latte.commons.toolkit.base.PagingUtils;

/**
 * @author zhen.ling
 *
 */
public class PagedParameter extends ExampleBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 页号
	 */
	private Integer page;

	/**
	 * 起始行号
	 *
	 */
	private Integer start;

	/**
	 * 单页行数
	 */
	private Integer rows;

	/**
	 * 排序项
	 */
	private String sort;

	/**
	 * 排序规则
	 */
	private Order order;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
