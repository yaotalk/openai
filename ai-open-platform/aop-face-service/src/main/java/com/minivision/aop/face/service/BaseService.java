package com.minivision.aop.face.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@DefaultProperties(commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
    @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "true")
})
public class BaseService {

}
