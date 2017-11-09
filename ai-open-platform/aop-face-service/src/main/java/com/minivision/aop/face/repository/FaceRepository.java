package com.minivision.aop.face.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.minivision.aop.face.entity.Face;

public interface FaceRepository extends CrudRepository<Face, String> {
  List<Face> findAll(Iterable<String> ids);
}
