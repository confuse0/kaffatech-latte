
package com.kaffatech.latte.mainframe.dto.paging;

import java.util.List;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.mainframe.dto.Response;

/**
 * @author zhen.ling
 *
 */
public class PagedRes<T> extends Response {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 标识
	 */
	private Integer draw;

	/**
	 * 总行数
	 */
	private Integer total;

	/**
	 * 列表数据
	 */
	private List<T> rows;

	/**
	 * 上页号
	 */
	private Integer prevPageNo;

	/**
	 * 下页号
	 */
	private Integer nextPageNo;

	/**
	 * 最大页
	 */
	private Integer maxPageNo;

	/**
	 * 页号列表
	 */
	private List<Integer> pageNoList;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
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
}
