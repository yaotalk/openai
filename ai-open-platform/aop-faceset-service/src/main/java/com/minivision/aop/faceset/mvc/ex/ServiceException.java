package com.minivision.aop.faceset.mvc.ex;

public class ServiceException extends Exception{

  private String msg;
  
  private Throwable throwable;
  
  //TODO error code
  
  public ServiceException(String msg, Throwable throwable) {
    this.msg = msg;
    this.throwable = throwable;
  }
  
  public ServiceException(String msg) {
    this.msg = msg;
  }
  
  public ServiceException(Throwable throwable) {
    this.msg = throwable.getMessage();
    this.throwable = throwable;
  }

  public String getMessage() {
    return msg;
  }


  public void setMessage(String msg) {
    this.msg = msg;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }
  
}
