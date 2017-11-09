package com.minivision.aop.auth.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minivision.ai.enumeration.Status;
import com.minivision.ai.rest.result.RestResult;
import com.minivision.aop.auth.domain.AppInfo;
import com.minivision.aop.auth.rest.param.CreateAppParam;
import com.minivision.aop.auth.rest.param.GetTokenParam;
import com.minivision.aop.auth.rest.param.UpdateAppParam;
import com.minivision.aop.auth.rest.result.TokenResult;
import com.minivision.aop.auth.service.AppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "api/v1", method = RequestMethod.POST)
@Api(tags = "AppApi", value = "AI Open Platform App Apis")
@Slf4j
public class AppApi {

  @Autowired
  private AppService appService;
  
  @RequestMapping(value = "/auth/createApp")
  @ApiOperation(value = "开发者创建应用", notes = "开发者创建应用")
  public RestResult<AppInfo> createApp(@Valid @ModelAttribute CreateAppParam param, BindingResult errResult) {
    RestResult<AppInfo> response = new RestResult<>();

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }
    
    AppInfo created = null;
    try {
      AppInfo exists = appService.findByUsernameAndAppName(param.getUsername(), param.getAppName());
      if (exists != null) {
        response.setErrorCode(Status.FAIL.getCode());
        response.setErrorMessage("应用【" + param.getAppName() + "】已存在");
        return response;
      }
      AppInfo app = new AppInfo();
      BeanUtils.copyProperties(param, app);
      created = appService.create(app);
    } catch (Throwable e) {
      log.error("开发者创建应用失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("开发者创建应用失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(created);
    return response;
  }
  
  @RequestMapping(value = "/auth/updateApp")
  @ApiOperation(value = "开发者修改应用信息", notes = "开发者修改应用信息")
  public RestResult<AppInfo> updateApp(@Valid @ModelAttribute UpdateAppParam param, BindingResult errResult) {
    RestResult<AppInfo> response = new RestResult<>();

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }
    
    AppInfo updated = null;
    try {
      AppInfo app = new AppInfo();
      BeanUtils.copyProperties(param, app);
      updated = appService.update(app);
    } catch (Throwable e) {
      log.error("开发者修改应用信息失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("开发者修改应用信息失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(updated);
    return response;
  }
  
  @RequestMapping(value = "/auth/deleteApp")
  @ApiOperation(value = "开发者删除应用", notes = "开发者删除应用")
  @ApiImplicitParams(value = {
      @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String", required = true),
      @ApiImplicitParam(name = "id", value = "应用主键ID", paramType = "query", dataType = "long", required = true)
  })
  public RestResult<String> deleteApp(@RequestParam("username") String username, @RequestParam("id") long id) {
    RestResult<String> response = new RestResult<>();
    
    if (id <= 0) {
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("应用主键ID无效");
      return response;
    }

    try {
      appService.delete(username, id);
    } catch (Throwable e) {
      log.error("开发者删除应用失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("开发者删除应用失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    return response;
  }
  
  @RequestMapping(value = "/auth/appList")
  @ApiOperation(value = "获取开发者应用列表", notes = "获取开发者应用列表")
  @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String", required = true)
  public RestResult<List<AppInfo>> appList(@RequestParam("username") String username) {
    RestResult<List<AppInfo>> response = new RestResult<>();
    List<AppInfo> apps = null;
    
    if (StringUtils.isBlank(username)) {
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("用户名不能为空");
      return response;
    }

    try {
      apps = appService.findAppListByUsername(username);
    } catch (Throwable e) {
      log.error("获取开发者应用列表失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("获取开发者应用列表失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(apps);
    return response;
  }
  
  @RequestMapping(value = "getAccessToken")
  @ApiOperation(value = "获取API访问令牌", notes = "获取API访问令牌")
  @ApiResponses(value = {
      @ApiResponse(code = 500, message = "获取API访问令牌失败")
  })
  public RestResult<List<TokenResult>> getAccessToken(@Valid @ModelAttribute GetTokenParam param, BindingResult errResult) {
    RestResult<List<TokenResult>> response = new RestResult<>();
    List<TokenResult> tokenList = null;
    
    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }

    try {
      tokenList = appService.getAccessToken(param);
    } catch (Throwable e) {
      log.error("获取API访问令牌失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("获取API访问令牌失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(tokenList);
    return response;
  }
  
}
