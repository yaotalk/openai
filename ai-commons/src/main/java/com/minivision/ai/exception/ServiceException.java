package com.minivision.ai.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 服务层异常
 * @author hughzhao
 * @2017年5月22日
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class ServiceException extends Exception {

	private static final long serialVersionUID = -5786981134077802501L;

	private String msg;

	private Throwable throwable;

	public ServiceException(String msg) {
		this.msg = msg;
	}

	public ServiceException(Throwable throwable) {
		this.msg = throwable.getMessage();
		this.throwable = throwable;
	}

}
