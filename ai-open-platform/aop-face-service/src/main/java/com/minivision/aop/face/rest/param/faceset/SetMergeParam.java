package com.minivision.aop.face.rest.param.faceset;

import org.hibernate.validator.constraints.NotBlank;

import com.minivision.aop.face.rest.param.RestParam;

public class SetMergeParam extends RestParam {

	private static final long serialVersionUID = 620915243975464379L;

	@NotBlank(message = "facesetToken1 must not be empty")
	private String facesetToken1;
	private String outerId1;
	
	@NotBlank(message = "facesetToken2 must not be empty")
	private String facesetToken2;
	private String outerId2;
	
	private String outerId;
	private String displayName;

	public String getFacesetToken1() {
		return facesetToken1;
	}

	public void setFacesetToken1(String facesetToken1) {
		this.facesetToken1 = facesetToken1;
	}

	public String getOuterId1() {
		return outerId1;
	}

	public void setOuterId1(String outerId1) {
		this.outerId1 = outerId1;
	}

	public String getFacesetToken2() {
		return facesetToken2;
	}

	public void setFacesetToken2(String facesetToken2) {
		this.facesetToken2 = facesetToken2;
	}

	public String getOuterId2() {
		return outerId2;
	}

	public void setOuterId2(String outerId2) {
		this.outerId2 = outerId2;
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
}
