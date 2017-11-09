package com.minivision.aop.portal.service;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("aop-face-service")
public interface FaceService {
  
}
