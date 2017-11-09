package com.minivision.aop.face.rest.result.faceset;

import java.util.List;

public class SetDetailResult {
	private String facesetToken;
	private String outerId;
	private String displayName;
	private int faceCount;
	private List<String> faceTokens;
	private int capacity;

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getFaceCount() {
		return faceCount;
	}

	public void setFaceCount(int faceCount) {
		this.faceCount = faceCount;
	}

	public List<String> getFaceTokens() {
		return faceTokens;
	}

	public void setFaceTokens(List<String> faceTokens) {
		this.faceTokens = faceTokens;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
