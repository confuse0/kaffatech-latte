
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
	 * 搜索项(不必填)
	 */
	private String key;

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
	 * 最大搜索总数(不必填)
	 */
	private Integer maxCount;

	/**
	 * 排序依据
	 */
	private String orderBy = "DEFAULT";

	/**
	 * 排序方式
	 */
	private String order = "DESC";

	/**
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}

	/**
	 * @param draw
	 *            the draw to set
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the maxCount
	 */
	public Integer getMaxCount() {
		return maxCount;
	}

	/**
	 * @param maxCount
	 *            the maxCount to set
	 */
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

}
