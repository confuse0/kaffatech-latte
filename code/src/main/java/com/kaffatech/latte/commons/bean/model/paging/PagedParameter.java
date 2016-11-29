
package com.kaffatech.latte.commons.bean.model.paging;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.commons.toolkit.base.PagingUtils;

/**
 * @author zhen.ling
 *
 */
public class PagedParameter extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer draw;

	private String orderBy;

	private Order order;

	private Integer offset;

	private Integer limit = PageConstant.LIMIT;

	private Integer maxCount;

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

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return PagingUtils.getPageNo(offset, limit);
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.offset = PagingUtils.getOffset(pageNo, limit);
	}

	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getNextOffset() {
		return offset + limit;
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

}
