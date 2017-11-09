package com.minivision.aop.faceset.rest.result;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "通用返回结构")
@JsonInclude(Include.NON_EMPTY)
@Getter
@Setter
public class RestResult<T> {

	private String requestId;
	private int timeUsed;
	private String errorMessage;
	@JsonUnwrapped
	private T data;

	public RestResult() {
		this.requestId = UUID.randomUUID().toString();
	}
	
	public RestResult(T data){
		this.data = data;
		this.requestId = UUID.randomUUID().toString();
	}
	
	public RestResult(Throwable t){
		this.errorMessage = t.getMessage();
		this.requestId = UUID.randomUUID().toString();
	}
	public RestResult(String msg){
		this.errorMessage = msg;
		this.requestId = UUID.randomUUID().toString();
	}

	@Override
	public String toString() {
		return "RestResult [requestId=" + requestId + ", timeUsed=" + timeUsed + ", errorMessage=" + errorMessage
				+ ", data=" + data + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requestId == null) ? 0 : requestId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestResult<?> other = (RestResult<?>) obj;
		if (requestId == null) {
			if (other.requestId != null)
				return false;
		} else if (!requestId.equals(other.requestId))
			return false;
		return true;
	}

}
