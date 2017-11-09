package com.minivision.aop.faceset.service;

import org.springframework.data.domain.Page;

import com.minivision.aop.faceset.domain.FaceSet;
import com.minivision.aop.faceset.mvc.ex.ServiceException;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface FaceSetService {
    List<FaceSet> findAll();

    List<FaceSet> findByFaceplat();

    FaceSet update(FaceSet faceSet);

    FaceSet create(FaceSet faceSet);

    FaceSet find(String token);

    void delete (String token) throws ServiceException;

    Set<FaceSet> findAll(String ids);

    Page<FaceSet> findAll(int page,int size);

    List<File> getSubFile(String filepath);
}
