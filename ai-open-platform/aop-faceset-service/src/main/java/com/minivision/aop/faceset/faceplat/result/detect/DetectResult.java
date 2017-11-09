package com.minivision.aop.faceset.faceplat.result.detect;

import java.util.List;

public class DetectResult {
	private String imageId;
	private List<DetectedFace> faces;

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
}
