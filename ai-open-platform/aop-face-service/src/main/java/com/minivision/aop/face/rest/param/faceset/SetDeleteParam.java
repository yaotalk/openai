package com.minivision.aop.face.rest.param.faceset;

import org.hibernate.validator.constraints.NotBlank;

import com.minivision.aop.face.rest.param.RestParam;

public class SetDeleteParam extends RestParam {

	private static final long serialVersionUID = -547891655429136565L;

	@NotBlank(message = "facesetToken must not be empty")
	private String facesetToken;
	
	private String outerId;
	
	private boolean force = false;

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

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

}
