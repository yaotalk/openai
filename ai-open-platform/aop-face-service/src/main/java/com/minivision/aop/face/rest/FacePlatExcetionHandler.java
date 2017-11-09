package com.minivision.aop.face.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.minivision.ai.rest.result.RestResult;
import com.minivision.aop.face.rest.result.ResultError;
import com.minivision.aop.face.service.ex.FacePlatException;
import com.netflix.hystrix.exception.HystrixRuntimeException;

@RestControllerAdvice
public class FacePlatExcetionHandler {
  
  @ExceptionHandler(FacePlatException.class)
  @ResponseBody
  public RestResult<ResultError> handleFacePlatException(HttpServletRequest request, FacePlatException ex) {
      ResultError re = new ResultError();
      re.setErrorMessage(ex.getMessage());
      re.setErrorCode(ex.getErrCode());
      re.setPath(request.getRequestURI());
      re.setTimestamp(System.currentTimeMillis());
      return new RestResult<>(re);
  }
  
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public RestResult<ResultError> handleArgException(HttpServletRequest request, IllegalArgumentException ex) {
      ResultError re = new ResultError();
      re.setErrorMessage(ex.getMessage());
      re.setErrorCode(400);
      re.setPath(request.getRequestURI());
      re.setTimestamp(System.currentTimeMillis());
      return new RestResult<>(re);
  }
  
  @ExceptionHandler(HystrixRuntimeException.class)
  @ResponseBody
  public RestResult<ResultError> handleControllerException(HttpServletRequest request, HystrixRuntimeException ex) {
      ResultError re = new ResultError();
      re.setErrorMessage(ex.getFailureType().toString());
      re.setErrorCode(HttpStatus.SERVICE_UNAVAILABLE.value());
      re.setPath(request.getRequestURI());
      re.setTimestamp(System.currentTimeMillis());
      return new RestResult<>(re);
  }
  
}
