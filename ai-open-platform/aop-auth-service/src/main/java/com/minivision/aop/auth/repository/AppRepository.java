package com.minivision.aop.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minivision.aop.auth.domain.AppInfo;

public interface AppRepository extends JpaRepository<AppInfo, Long> {

  List<AppInfo> findByUsername(String username);
  
  AppInfo findByAppId(String appId);
  
  AppInfo findByUsernameAndAppName(String username, String appName);
  
}
