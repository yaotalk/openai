package com.minivision.aop.face.rest.param.detect;

import org.springframework.web.multipart.MultipartFile;

import com.minivision.aop.face.rest.param.RestParam;

public class DetectParam extends RestParam {

	private static final long serialVersionUID = -292864702752714956L;
	
	private String imageUrl;
	
	//@NotNull(message = "imageFile must not be empty")
	private MultipartFile imageFile;
	
	private boolean faceAttributes;

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
	
	public boolean isFaceAttributes() {
      return faceAttributes;
    }
  
    public void setFaceAttributes(boolean faceAttributes) {
      this.faceAttributes = faceAttributes;
    }

    @Override
    public String toString() {
      return "DetectParam [imageUrl=" + imageUrl + ", imageFile=" + imageFile + ", faceAttributes="
          + faceAttributes + "]";
    }

}
