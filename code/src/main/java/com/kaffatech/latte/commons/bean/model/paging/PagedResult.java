
package com.kaffatech.latte.commons.bean.model.paging;

import java.util.ArrayList;
import java.util.List;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class PagedResult<T> extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer draw;

	private Integer recordTotal = 0;

	private Integer recordFiltered;

	private List<T> list = new ArrayList<T>();

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
