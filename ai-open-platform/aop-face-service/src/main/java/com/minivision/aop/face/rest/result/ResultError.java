package com.minivision.aop.face.rest.result;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "error")
public class ResultError {
	private int errorCode;
	private long timestamp;
	private String errorMessage;
	private String path;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	

	public int getErrorCode() {
      return errorCode;
    }
  
    public void setErrorCode(int errorCode) {
      this.errorCode = errorCode;
    }
  
    public String getErrorMessage() {
      return errorMessage;
    }
  
    public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
    }
  
    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
