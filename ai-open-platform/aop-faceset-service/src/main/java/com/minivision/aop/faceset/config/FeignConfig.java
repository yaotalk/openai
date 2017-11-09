package com.minivision.aop.faceset.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static java.util.concurrent.TimeUnit.SECONDS;

import feign.Request;
import feign.Retryer;

@Configuration
public class FeignConfig {

  @Bean
  public Retryer feginRetryer(){
    Retryer retryer = new Retryer.Default(100, SECONDS.toMillis(2), 3);
    return retryer;
  }

  @Bean
  public feign.Logger.Level multipartLoggerLevel() {
    return feign.Logger.Level.FULL;
  }

  @Bean
  public Request.Options feginOption(){
    Request.Options option = new Request.Options(5000, 10000);
    return option;
  }

}
