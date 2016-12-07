
package com.kaffatech.latte.mainframe.dto.paging;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class PagedReq extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 标识
	 */
	private Integer draw;

	/**
	 * 页号
	 */
	private Integer page;

	/**
	 * 起始行号
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
	private String order;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
