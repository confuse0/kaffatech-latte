
package com.kaffatech.latte.mainframe.dto.paging;

import com.kaffatech.latte.commons.bean.model.paging.PageConstant;
import com.kaffatech.latte.mainframe.dto.Request;

/**
 * @author zhen.ling
 *
 */
public class BasePagedReq extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8970062319769618446L;

	/**
	 * 标识(不必填)
	 */
	private Integer draw;

	/**
	 * 过滤项(不必填)
	 */
	private String filter;

	/**
	 * 页号
	 */
	private Integer pageNo = 1;

	/**
	 * 一页数量
	 */
	private Integer limit = PageConstant.LIMIT;

	/**
	 * 排序依据
	 */
	private String orderBy = "DEFAULT";

	/**
	 * 排序方式
	 */
	private String order = "DESC";

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
