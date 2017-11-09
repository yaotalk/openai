package com.minivision.aop.auth.service;

import com.minivision.aop.auth.domain.Developer;
import com.minivision.aop.auth.rest.result.LoginResult;

public interface DeveloperService {

  Developer create(Developer developer);
  Developer update(Developer developer);
  
  Developer findByUsername(String username);
  LoginResult login(Developer developer);
  
}
