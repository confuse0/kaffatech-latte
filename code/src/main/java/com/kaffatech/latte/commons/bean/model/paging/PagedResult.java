
package com.kaffatech.latte.commons.bean.model.paging;

import com.kaffatech.latte.commons.bean.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhen.ling
 *
 */
public class PagedResult<T> extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 总行数
	 */
	private Integer total = 0;

	/**
	 * 列表数据
	 */
	private List<T> rows = new ArrayList<T>();

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
}
