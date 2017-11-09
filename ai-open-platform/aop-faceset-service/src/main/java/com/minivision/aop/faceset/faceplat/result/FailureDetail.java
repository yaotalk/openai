package com.minivision.aop.faceset.faceplat.result;

public class FailureDetail {
	private String faceToken;
	private String reason;

	public FailureDetail(String faceToken, String reason) {
		this.faceToken = faceToken;
		this.reason = reason;
	}

	public String getFaceToken() {
		return faceToken;
	}

	public void setFaceToken(String faceToken) {
		this.faceToken = faceToken;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
