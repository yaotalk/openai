package com.minivision.aop.face.service;

import com.minivision.aop.face.rest.result.faceset.AddFaceResult;
import com.minivision.aop.face.rest.result.faceset.RemoveFaceResult;
import com.minivision.aop.face.rest.result.faceset.SetCreateResult;
import com.minivision.aop.face.rest.result.faceset.SetDeleteResult;
import com.minivision.aop.face.rest.result.faceset.SetDetailResult;
import com.minivision.aop.face.rest.result.faceset.SetListResult;
import com.minivision.aop.face.rest.result.faceset.SetMergeResult;
import com.minivision.aop.face.rest.result.faceset.SetModifyResult;
import com.minivision.aop.face.service.ex.FacePlatException;

public interface FaceSetService {

	/**
	 * Create a faceset
	 * 
	 * @param owner
	 *            Use appKey
	 * @param capacity 
	 * @return The facesetToken
	 */
	public SetCreateResult create(String owner, String outerId, String displayName, int capacity) throws FacePlatException;
	
	public SetCreateResult create(String owner, String outerId, String displayName) throws FacePlatException;
	
	/**
	 * Add faces to faceset
	 * 
	 * @param setToken
	 *            The dest faceset
	 * @param faceTokens
	 *            The faces to be added
	 * @return Operation result
	 * @throws FacePlatException
	 */
	public AddFaceResult addFace(String setToken, String... faceTokens) throws FacePlatException;

	/**
	 * Delete a faceset
	 * 
	 * @param owner
	 *            Use appKey
	 * @param force
	 *            Delete or not if the faceset is not empty
	 * @return The facesetToken
	 */
	public SetDeleteResult delete(String setToken, boolean force) throws FacePlatException;

	/**
	 * remove faces to faceset
	 * @param setToken
	 * @param faceTokens
	 * @return
	 * @throws FacePlatException
	 */
	public RemoveFaceResult removeFace(String setToken, String... faceTokens) throws FacePlatException;
	
	/**
	 * modify faceset
	 * @param setToken
	 * @param displayName
	 * @return
	 * @throws FacePlatException
	 */
	public SetModifyResult modify(String setToken, String displayName, int capacity) throws FacePlatException;
	
	//public SetModifyResult modify(String setToken, String displayName) throws FacePlatException;

	/**
	 * get faceset detail message
	 * @param setToken
	 * @return SetDetailResult with filled property "faces"
	 * @throws FacePlatException
	 */
	public SetDetailResult getFaceSetDetail(String setToken, long offset, long count) throws FacePlatException;
	
	
	/**
	 * @param offset
	 * @param count
	 * @return
	 * @throws FacePlatException
	 */
	public SetListResult getFaceSetList(int offset, int count) throws FacePlatException;

	/**
	 * merge faceset1 and faceset2
	 * @param setToken1
	 * @param setToken2
	 * @return
	 * @throws FacePlatException
	 */
	public SetMergeResult mergeFace(String setToken1, String setToken2) throws FacePlatException;

}
