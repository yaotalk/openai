package com.minivision.aop.face.rest.param.detect;

import org.springframework.web.multipart.MultipartFile;

import com.minivision.aop.face.rest.param.RestParam;

public class CompareParam extends RestParam {

	private static final long serialVersionUID = -4720625030329496752L;

	private String faceToken1;
	private String imageUrl1;
	private MultipartFile imageFile1;

	private String faceToken2;
	private String imageUrl2;
	private MultipartFile imageFile2;

	public String getFaceToken1() {
		return faceToken1;
	}

	public void setFaceToken1(String faceToken1) {
		this.faceToken1 = faceToken1;
	}

	public String getImageUrl1() {
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public MultipartFile getImageFile1() {
		return imageFile1;
	}

	public void setImageFile1(MultipartFile imageFile1) {
		this.imageFile1 = imageFile1;
	}

	public String getFaceToken2() {
		return faceToken2;
	}

	public void setFaceToken2(String faceToken2) {
		this.faceToken2 = faceToken2;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public MultipartFile getImageFile2() {
		return imageFile2;
	}

	public void setImageFile2(MultipartFile imageFile2) {
		this.imageFile2 = imageFile2;
	}

  @Override
  public String toString() {
    return "CompareParam [faceToken1=" + faceToken1 + ", imageUrl1=" + imageUrl1 + ", imageFile1="
        + imageFile1 + ", faceToken2=" + faceToken2 + ", imageUrl2=" + imageUrl2 + ", imageFile2="
        + imageFile2 + "]";
  }

}
