package com.minivision.aop.portal.service;

import java.util.List;
import java.util.Map;

import com.minivision.aop.portal.param.CreateAppParam;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import com.minivision.ai.rest.result.RestResult;
import com.minivision.common.domain.app.AppInfo;
import com.minivision.common.domain.app.Developer;

//@FeignClient("AOP-AUTH-SERVICE")
@FeignClient("aop-auth-service")
public interface UserService {
  @PostMapping("/uaa/api/v1/auth/appList")
  RestResult<List<AppInfo>> appList(@RequestParam("username") String username);
  
  @PostMapping(value = "/uaa/api/v1/login")
  RestResult<Developer> checkLogin(@RequestParam("username")String username, @RequestParam("password")String password);

  @PostMapping(value = "/uaa/api/v1/auth/createApp")
  RestResult<AppInfo> createApp( @RequestParam Map param);

  @PostMapping(value = "/uaa/api/v1/auth/updateApp")
  RestResult<AppInfo> updateApp(@RequestParam Map param);

  @PostMapping(value = "/uaa/api/v1/auth/deleteApp")
  RestResult<String> delete(@RequestParam("username")String name,@RequestParam("id")long id);
}
