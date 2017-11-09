package com.minivision.aop.content.hystrix;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.strategy.HystrixPlugins;

@Component
public class HystrixPluginsInitializer {
  
  @PostConstruct
  public void init() {
    HystrixPlugins.getInstance().registerCommandExecutionHook(HystrixRequestCacheListener.getInstance());
  }

}
