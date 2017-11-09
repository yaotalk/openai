package com.minivision.aop.face.service.ex;

public class FacePlatException extends Exception {

	private static final long serialVersionUID = 230466881538432171L;

	private int errCode;
	
	private Throwable cause;

	public FacePlatException(int errCode) {
		this.errCode = errCode;
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
		return String.format("[%s:%s] ", errCode, ErrorType.getDesc(errCode));
	}

}
