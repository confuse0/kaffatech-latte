
package com.kaffatech.latte.commons.log;

import com.kaffatech.latte.commons.bean.model.IdBean;

import java.util.Date;

/**
 * @author zhen.ling
 *
 */
public class Digest extends IdBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 未知
	 */
	public static final String X_FACTOR = "X";

	/**
	 * 成功
	 */
	public static final String SUCCESS = "S";

	/**
	 * 失败
	 */
	public static final String ERROR = "E";

	/**
	 * URL
	 */
	private String url;

	/**
	 * 操作
	 */
	private String op;

	/**
	 * 来源信息
	 */
	private String fromInfo;

	/**
	 * 执行者
	 */
	private String executor;

	/**
	 * 请求
	 */
	private Object req;

	/**
	 * 响应
	 */
	private Object res;

	/**
	 * 响应码
	 */
	private String code;

	/**
	 * 响应消息
	 */
	private String message;

	/**
	 * 处理状态
	 */
	private String status;

	/**
	 * 花费时间
	 */
	private Long cost;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间(一般不数据订正啥的跟创建时间一样)
	 */
	private Date updateTime;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getFromInfo() {
		return fromInfo;
	}

	public void setFromInfo(String fromInfo) {
		this.fromInfo = fromInfo;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public Object getReq() {
		return req;
	}

	public void setReq(Object req) {
		this.req = req;
	}

	public Object getRes() {
		return res;
	}

	public void setRes(Object res) {
		this.res = res;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
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
}
