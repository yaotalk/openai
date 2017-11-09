package com.minivision.aop.face.rest.param.detect;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.minivision.aop.face.rest.param.RestParam;

public class SearchParam extends RestParam {

	private static final long serialVersionUID = 4331892568980284171L;

	private String faceToken;
	private String imageUrl;
	private MultipartFile imageFile;

	@NotBlank(message = "facesetToken must not be empty")
	private String facesetToken;

	@Max(100)
	private int resultCount = 1;

	public String getFaceToken() {
		return faceToken;
	}

	public void setFaceToken(String faceToken) {
		this.faceToken = faceToken;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getFacesetToken() {
		return facesetToken;
	}

	public void setFacesetToken(String facesetToken) {
		this.facesetToken = facesetToken;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

  @Override
  public String toString() {
    return "SearchParam [faceToken=" + faceToken + ", imageUrl=" + imageUrl + ", imageFile="
        + imageFile + ", facesetToken=" + facesetToken + ", resultCount=" + resultCount + "]";
  }
	
}
