package com.minivision.aop.faceset.faceplat.ex;

import org.apache.commons.lang3.StringUtils;

public class FacePlatException extends RuntimeException {

	private static final long serialVersionUID = 230466881538432171L;

	private int errCode;
	
	private String message;
	
	private Throwable cause;

	public FacePlatException(int errCode) {
		this.errCode = errCode;
	}
	
	public FacePlatException(int errCode, String message) {
      this.errCode = errCode;
      this.message = message;
  }
	
	public FacePlatException(int errCode, Throwable cause) {
		this.errCode = errCode;
		this.cause = cause;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public String getMessage() {
	  String msg = StringUtils.isNotEmpty(message)?message: ErrorType.getDesc(errCode);
	  return String.format("[%s], %s", errCode, msg);
	}

}
