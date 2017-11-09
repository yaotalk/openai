package com.minivision.aop.portal.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.minivision.ai.rest.result.RestResult;
import com.minivision.common.domain.app.Developer;

@RestController
@CrossOrigin
@RequestMapping("login")
public class LoginController {

  @GetMapping("/currentUser")
  public RestResult<Developer> loginSuccess() {
    if( SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser"){
      return new RestResult<>();
    }
    Developer developer = (Developer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return new RestResult<>(developer);
  }
  
  @PostMapping("login/failure")
  public RestResult<Developer> loginFailure() {
    return new RestResult<>();
  }
}
