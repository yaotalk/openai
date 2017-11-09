package com.minivision.aop.face.rest.param.faceset;

import org.hibernate.validator.constraints.NotBlank;

import com.minivision.aop.face.rest.param.RestParam;

public class RemoveFaceParam extends RestParam {

	private static final long serialVersionUID = -7985661554574219225L;
	
	@NotBlank(message = "facesetToken must not be empty")
	private String facesetToken;
	
	private String outerId;
	
	@NotBlank(message = "faceTokens must not be empty")
	private String faceTokens;

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

	public String getFaceTokens() {
		return faceTokens;
	}

	public void setFaceTokens(String faceTokens) {
		this.faceTokens = faceTokens;
	}
}
