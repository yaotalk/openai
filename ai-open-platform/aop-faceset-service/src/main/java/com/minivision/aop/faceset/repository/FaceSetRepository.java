package com.minivision.aop.faceset.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.minivision.aop.faceset.domain.FaceSet;

import java.util.List;

public interface FaceSetRepository extends PagingAndSortingRepository<FaceSet, String> {
    List<FaceSet> findAll();
}
