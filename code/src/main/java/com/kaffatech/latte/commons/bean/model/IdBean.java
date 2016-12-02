
package com.kaffatech.latte.commons.bean.model;

import com.kaffatech.latte.commons.bean.model.type.BooleanType;

import java.util.Date;

/**
 * @author zhen.ling
 *
 */
public class IdBean extends BaseBean {

	/**
	 * 版本序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 删除标记(0:未删除;1:已删除)
	 */
	private BooleanType deleteFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BooleanType getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(BooleanType deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
