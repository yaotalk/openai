package com.minivision.aop.face.service;

import java.util.List;

import com.minivision.aop.face.rest.result.detect.CompareResult;
import com.minivision.aop.face.rest.result.detect.DetectedFace;
import com.minivision.aop.face.rest.result.detect.SearchResult;
import com.minivision.aop.face.service.ex.FacePlatException;


public interface FaceService {

	public List<DetectedFace> detect(byte[] img) throws FacePlatException;
	
	public List<DetectedFace> detect(byte[] img, boolean attributes) throws FacePlatException;
	
	public List<DetectedFace> getFaceAttribute(byte[] img) throws FacePlatException;

	public CompareResult compare(String faceToken1, String faceToken2, byte[] img1, byte[] img2)
			throws FacePlatException;

	public SearchResult search(String faceToken, byte[] img, String facesetToken, int count) throws FacePlatException;

}
