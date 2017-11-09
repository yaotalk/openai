package com.minivision.aop.face.rest.result.faceset;

import java.util.List;

import com.minivision.aop.face.rest.result.FailureDetail;

public class RemoveFaceResult {
	private String facesetToken;
	private String outerId;
	private int faceRemoved;
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

	public int getFaceRemoved() {
		return faceRemoved;
	}

	public void setFaceRemoved(int faceRemoved) {
		this.faceRemoved = faceRemoved;
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
