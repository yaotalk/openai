package com.minivision.aop.face.rest.result.detect;

import java.util.List;

public class CompareResult {
	private double confidence;
	private String imageId1;
	private String imageId2;
	private List<DetectedFace> faces1;
	private List<DetectedFace> faces2;

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String getImageId1() {
		return imageId1;
	}

	public void setImageId1(String imageId1) {
		this.imageId1 = imageId1;
	}

	public String getImageId2() {
		return imageId2;
	}

	public void setImageId2(String imageId2) {
		this.imageId2 = imageId2;
	}

	public List<DetectedFace> getFaces1() {
		return faces1;
	}

	public void setFaces1(List<DetectedFace> faces1) {
		this.faces1 = faces1;
	}

	public List<DetectedFace> getFaces2() {
		return faces2;
	}

	public void setFaces2(List<DetectedFace> faces2) {
		this.faces2 = faces2;
	}
}
