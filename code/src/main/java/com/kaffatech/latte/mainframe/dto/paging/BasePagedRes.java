
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

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
