package com.minivision.aop.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minivision.aop.auth.domain.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

  Developer findByUsername(String username);
  
}
