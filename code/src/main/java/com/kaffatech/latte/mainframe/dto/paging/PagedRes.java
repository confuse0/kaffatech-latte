
package com.kaffatech.latte.mainframe.dto.paging;

import java.util.List;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 *
 */
public class PagedRes<T> extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4400736971865310797L;

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
