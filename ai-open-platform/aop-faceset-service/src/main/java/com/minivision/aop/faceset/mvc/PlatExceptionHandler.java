package com.minivision.aop.faceset.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.minivision.aop.faceset.faceplat.ex.FacePlatException;


@RestControllerAdvice
public class PlatExceptionHandler {
  @ExceptionHandler(FacePlatException.class)
  @ResponseBody
  public String handleFacePlatException(HttpServletRequest request, FacePlatException ex) {
    //TODO JSON
      return "failure";
  }
}
