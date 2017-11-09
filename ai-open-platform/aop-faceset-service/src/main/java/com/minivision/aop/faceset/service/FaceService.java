package com.minivision.aop.faceset.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.minivision.aop.faceset.domain.Face;
import com.minivision.aop.faceset.mvc.ex.ServiceException;
import com.minivision.aop.faceset.rest.param.faceset.FaceSearchParam;
import com.minivision.aop.faceset.rest.param.faceset.FacesetParam;
import com.minivision.aop.faceset.rest.result.faceset.FaceSearchResult;

public interface FaceService {
	
    void save(Face face, String facesetToken, byte[] imgData, String fileType) throws ServiceException;

    void update(Face face);

    void delete(String faceToken, String facesetToken);
    
    Face find(String faceToken);

    Page<Face> findByFacesetId(FacesetParam facesetParam);

    List<FaceSearchResult> searchByPlat(FaceSearchParam faceSearchParam) throws ServiceException;

}
