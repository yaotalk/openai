package com.minivision.aop.auth.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.minivision.ai.util.ParamUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestLogInterceptor extends HandlerInterceptorAdapter {

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    log.info("Request:");
    String url = request.getRequestURL().toString();
    log.info(url);
    log.info(ParamUtil.getParamString(request));
    log.info("Response:");
    log.info("" + response.getStatus());
    if (handler instanceof HandlerMethod) {
      log.info(((HandlerMethod)handler).getShortLogMessage());
    }
    if (ex != null) {
      log.error("请求异常：" + url, ex);
    }
    super.afterCompletion(request, response, handler, ex);
  }
  
}
