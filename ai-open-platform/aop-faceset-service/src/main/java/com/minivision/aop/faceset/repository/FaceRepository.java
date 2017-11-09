package com.minivision.aop.faceset.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.minivision.aop.faceset.domain.Face;

import java.util.List;

public interface FaceRepository extends PagingAndSortingRepository<Face, String> ,JpaSpecificationExecutor<Face> {
    void deleteByIdIn(List<String> ids);
    Page<Face> findByFaceSetToken(String facesetToken,Specification<Face> specification,Pageable pageable);
}
