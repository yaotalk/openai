package com.minivision.aop.auth.rest.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TokenResult {

  private String apiGroup;
  private String token_type;
  private String access_token;
  private long expires_in;
  
}
