
package com.kaffatech.latte.mainframe.dto.paging;

import java.util.List;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class BasePagedRes<T> extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 标识(不必填)
	 */
	private Integer draw;

	/**
	 * 总数
	 */
	private Integer recordTotal;

	/**
	 * 总页数
	 */
	private Integer maxPageNo;

	/**
	 * 过滤数(不必填)
	 */
	private Integer recordFiltered;

	/**
	 * 分页号列表
	 */
	private List<Integer> pageNoList;

	/**
	 * 上页号
	 */
	private Integer prevPageNo;

	/**
	 * 上页号
	 */
	private Integer nextPageNo;

	/**
	 * 数据
	 */
	private List<T> list;

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
	 * @return the recordTotal
	 */
	public Integer getRecordTotal() {
		return recordTotal;
	}

	/**
	 * @param recordTotal
	 *            the recordTotal to set
	 */
	public void setRecordTotal(Integer recordTotal) {
		this.recordTotal = recordTotal;
	}

	/**
	 * @return the maxPageNo
	 */
	public Integer getMaxPageNo() {
		return maxPageNo;
	}

	/**
	 * @param maxPageNo
	 *            the maxPageNo to set
	 */
	public void setMaxPageNo(Integer maxPageNo) {
		this.maxPageNo = maxPageNo;
	}

	/**
	 * @return the recordFiltered
	 */
	public Integer getRecordFiltered() {
		return recordFiltered;
	}

	/**
	 * @param recordFiltered
	 *            the recordFiltered to set
	 */
	public void setRecordFiltered(Integer recordFiltered) {
		this.recordFiltered = recordFiltered;
	}

	/**
	 * @return the pageNoList
	 */
	public List<Integer> getPageNoList() {
		return pageNoList;
	}

	/**
	 * @param pageNoList
	 *            the pageNoList to set
	 */
	public void setPageNoList(List<Integer> pageNoList) {
		this.pageNoList = pageNoList;
	}

	/**
	 * @return the prevPageNo
	 */
	public Integer getPrevPageNo() {
		return prevPageNo;
	}

	/**
	 * @param prevPageNo
	 *            the prevPageNo to set
	 */
	public void setPrevPageNo(Integer prevPageNo) {
		this.prevPageNo = prevPageNo;
	}

	/**
	 * @return the nextPageNo
	 */
	public Integer getNextPageNo() {
		return nextPageNo;
	}

	/**
	 * @param nextPageNo
	 *            the nextPageNo to set
	 */
	public void setNextPageNo(Integer nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

}
