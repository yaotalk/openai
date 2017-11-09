package com.minivision.aop.face.rest.param.faceset;

import javax.validation.constraints.Max;
import org.hibernate.validator.constraints.NotBlank;

import com.minivision.aop.face.rest.param.RestParam;

public class SetDetailParam extends RestParam {

	private static final long serialVersionUID = 1407314840533961427L;

	@NotBlank(message = "facesetToken must not be empty")
	private String facesetToken;

	private String outerId;

	private int faceOffset = 0;
	
	@Max(100)
	private int faceCount;

	public String getFacesetToken() {
		return facesetToken;
	}

	public void setFacesetToken(String facesetToken) {
		this.facesetToken = facesetToken;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public int getFaceOffset() {
		return faceOffset;
	}

	public void setFaceOffset(int faceOffset) {
		this.faceOffset = faceOffset;
	}

	public int getFaceCount() {
		return faceCount;
	}

	public void setFaceCount(int faceCount) {
		this.faceCount = faceCount;
	}
}
