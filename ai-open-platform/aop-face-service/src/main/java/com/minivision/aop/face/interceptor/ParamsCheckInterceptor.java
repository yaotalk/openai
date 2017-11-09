package com.minivision.aop.face.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ParamsCheckInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String developerId = request.getParameter("developerId");
    String appId = request.getParameter("appId");
    if (StringUtils.hasText(developerId) && StringUtils.hasText(appId)) {
      return true;
    }
    
    response.sendError(HttpServletResponse.SC_FORBIDDEN, "缺少必需的参数：developerId、appId");
    return false;
  }
  
}
