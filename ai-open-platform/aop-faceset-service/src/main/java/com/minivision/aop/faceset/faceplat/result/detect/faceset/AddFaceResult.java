package com.minivision.aop.faceset.faceplat.result.detect.faceset;

import java.util.List;

import com.minivision.aop.faceset.faceplat.result.FailureDetail;

public class AddFaceResult {
	private String facesetToken;
	private String outerId;
	private int faceAdded;
	private int faceCount;
	private List<FailureDetail> failureDetail;

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

	public int getFaceAdded() {
		return faceAdded;
	}

	public void setFaceAdded(int faceAdded) {
		this.faceAdded = faceAdded;
	}

	public int getFaceCount() {
		return faceCount;
	}

	public void setFaceCount(int faceCount) {
		this.faceCount = faceCount;
	}

	public List<FailureDetail> getFailureDetail() {
		return failureDetail;
	}

	public void setFailureDetail(List<FailureDetail> failureDetail) {
		this.failureDetail = failureDetail;
	}

}
