package com.minivision.aop.portal.rest;

import java.util.List;

import com.minivision.aop.portal.param.CreateAppParam;
import com.minivision.aop.portal.param.UpdateAppParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.minivision.ai.rest.result.RestResult;
import com.minivision.aop.portal.service.UserService;
import com.minivision.common.domain.app.AppInfo;
import com.minivision.common.domain.app.Developer;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/appmanage")
@PreAuthorize("isAuthenticated()")
public class AppManageController {
  @Autowired
  private UserService userService;
  
  @GetMapping("/applist")
  public RestResult<List<AppInfo>> appList() {
    Developer developer = (Developer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userService.appList(developer.getUsername());
  }
  
  @GetMapping("/applist/{username}")
  @PreAuthorize("(#username == principal.username) or hasRole('ADMIN')")
  public RestResult<List<AppInfo>> appList(@PathVariable("username") String username) {
    return userService.appList(username);
  }

  @PostMapping("createApp")
  public RestResult<AppInfo> create(@Valid @ModelAttribute CreateAppParam param){
    Developer developer = (Developer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    param.setUsername(developer.getUsername());
    BeanMap beanMap = BeanMap.create(param);
    return userService.createApp(beanMap);
  }

  @PostMapping("updateApp")
  public RestResult<AppInfo> update(@Valid @ModelAttribute UpdateAppParam param){
    Developer developer = (Developer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    param.setUsername(developer.getUsername());
    BeanMap beanMap = BeanMap.create(param);
    return userService.updateApp(beanMap);
  }

  @PostMapping("deleteApp")
  public RestResult<String> delete(@RequestParam("id")long id){
    Developer developer = (Developer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userService.delete(developer.getUsername(),id);
  }
}
