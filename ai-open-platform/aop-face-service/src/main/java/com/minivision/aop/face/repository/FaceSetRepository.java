package com.minivision.aop.face.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.minivision.aop.face.entity.FaceSet;

public interface FaceSetRepository extends PagingAndSortingRepository<FaceSet, String> {

}
