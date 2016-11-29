
package com.kaffatech.latte.mainframe.model.exception;

import com.kaffatech.latte.mainframe.model.type.ResultCode;

/**
 * @author zhen.ling
 *
 */
public class BizException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	public BizException(ResultCode resultCode) {
		this(resultCode.getCode(), resultCode.getMessage());
	}

	public BizException(ResultCode resultCode, Object... args) {
		this(resultCode.getCode(), String.format(resultCode.getMessage(), args));
	}

	public BizException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BizException(ResultCode resultCode, Throwable cause) {
		this(resultCode.getCode(), resultCode.getMessage(), cause);
	}

	public BizException(ResultCode resultCode, Throwable cause, Object... args) {
		this(resultCode.getCode(), String.format(resultCode.getMessage(), args), cause);
	}

	public BizException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

}
