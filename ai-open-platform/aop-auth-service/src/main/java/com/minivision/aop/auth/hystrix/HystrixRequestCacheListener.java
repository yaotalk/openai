package com.minivision.aop.auth.hystrix;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.contrib.javanica.command.GenericCommand;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HystrixRequestCacheListener extends HystrixCommandExecutionHook {
  
  private static HystrixRequestCacheListener INSTANCE = new HystrixRequestCacheListener();
  
  private HystrixRequestCacheListener() {}
  
  public static HystrixRequestCacheListener getInstance() {
    return INSTANCE;
  }

  @Override
  public <T> void onCacheHit(HystrixInvokable<T> commandInstance) {
    if (commandInstance instanceof GenericCommand) {
      GenericCommand command = (GenericCommand) commandInstance;
      log.info("{}.{} request cache hits, cache key:{}", command.getCommandGroup().name(), command.getCommandKey().name(), command.getPublicCacheKey());
    }
  }
  
}
