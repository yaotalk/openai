package com.minivision.aop.face.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.minivision.aop.face.interceptor.ParamsCheckInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Bean
  ParamsCheckInterceptor paramsCheckInterceptor() {
    return new ParamsCheckInterceptor();
  }
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(paramsCheckInterceptor())
            .addPathPatterns("/api/v1/**");
  }
  
}
