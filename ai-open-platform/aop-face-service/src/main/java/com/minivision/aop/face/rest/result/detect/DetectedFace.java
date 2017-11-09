package com.minivision.aop.face.rest.result.detect;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DetectedFace {
	private String faceToken;
	private Rectangle faceRectangle;
	private FaceAttribute faceAttribute;
	@JsonIgnore
	private float[] feature;

	public String getFaceToken() {
		return faceToken;
	}

	public void setFaceToken(String faceToken) {
		this.faceToken = faceToken;
	}

	public Rectangle getFaceRectangle() {
		return faceRectangle;
	}

	public void setFaceRectangle(Rectangle faceRectangle) {
		this.faceRectangle = faceRectangle;
	}
	
	public FaceAttribute getFaceAttribute() {
      return faceAttribute;
    }

    public void setFaceAttribute(FaceAttribute faceAttribute) {
      this.faceAttribute = faceAttribute;
    }

    public float[] getFeature() {
		return feature;
	}

	public void setFeature(float[] feature) {
		this.feature = feature;
	}

	public class Rectangle {
		private int top;
		private int left;
		private int width;
		private int height;

		public int getTop() {
			return top;
		}

		public void setTop(int top) {
			this.top = top;
		}

		public int getLeft() {
			return left;
		}

		public void setLeft(int left) {
			this.left = left;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}

}
