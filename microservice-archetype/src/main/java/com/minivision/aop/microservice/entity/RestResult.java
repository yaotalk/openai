package com.minivision.aop.microservice.entity;


public class RestResult<T> {
  
  private int status;
  private int infoCode;
  private String info;
  
  private T data;
  
  public RestResult(T data){
    this.status = 1;
    this.infoCode = 10000;
    this.info = "ok";
    this.data = data;
  }
  
  public RestResult(T data, int status, int infoCode){
    this.status = status;
    this.infoCode = infoCode;
    this.data = data;
  }
  
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getInfoCode() {
    return infoCode;
  }

  public void setInfoCode(int infoCode) {
    this.infoCode = infoCode;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
