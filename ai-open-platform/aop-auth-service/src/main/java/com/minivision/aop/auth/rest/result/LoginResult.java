package com.minivision.aop.auth.rest.result;

import com.minivision.aop.auth.domain.Developer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResult {
  
  private Developer developer;
  private String message;
  
}
