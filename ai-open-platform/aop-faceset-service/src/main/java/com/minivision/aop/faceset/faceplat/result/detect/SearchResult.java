package com.minivision.aop.faceset.faceplat.result.detect;

import java.util.List;

public class SearchResult {
	private String imageId;
	private String faceToken;
	private List<DetectedFace> faces;
	private List<Result> results;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public List<DetectedFace> getFaces() {
		return faces;
	}

	public void setFaces(List<DetectedFace> faces) {
		this.faces = faces;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getFaceToken() {
		return faceToken;
	}

	public void setFaceToken(String faceToken) {
		this.faceToken = faceToken;
	}

	public static class Result {
		private String faceToken;
		private double confidence;

		public String getFaceToken() {
			return faceToken;
		}

		public void setFaceToken(String faceToken) {
			this.faceToken = faceToken;
		}

		public double getConfidence() {
			return confidence;
		}

		public void setConfidence(double confidence) {
			this.confidence = confidence;
		}

	}
}
