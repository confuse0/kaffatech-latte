
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
	 * 过滤数(不必填)
	 */
	private Integer recordFiltered;

	/**
	 * 上一页
	 */
	private Integer prevPageNo;

	/**
	 * 下一页
	 */
	private Integer nextPageNo;

	/**
	 * 最大页
	 */
	private Integer maxPageNo;

	/**
	 * 页号List
	 */
	private List<Integer> pageNoList;

	/**
	 * 数据
	 */
	private List<T> list;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(Integer recordTotal) {
		this.recordTotal = recordTotal;
	}

	public Integer getRecordFiltered() {
		return recordFiltered;
	}

	public void setRecordFiltered(Integer recordFiltered) {
		this.recordFiltered = recordFiltered;
	}

	public Integer getPrevPageNo() {
		return prevPageNo;
	}

	public void setPrevPageNo(Integer prevPageNo) {
		this.prevPageNo = prevPageNo;
	}

	public Integer getNextPageNo() {
		return nextPageNo;
	}

	public void setNextPageNo(Integer nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public Integer getMaxPageNo() {
		return maxPageNo;
	}

	public void setMaxPageNo(Integer maxPageNo) {
		this.maxPageNo = maxPageNo;
	}

	public List<Integer> getPageNoList() {
		return pageNoList;
	}

	public void setPageNoList(List<Integer> pageNoList) {
		this.pageNoList = pageNoList;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
