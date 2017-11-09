package com.minivision.aop.auth.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.minivision.aop.auth.config.AuthProperties;
import com.minivision.aop.auth.domain.AppInfo;
import com.minivision.aop.auth.repository.AppRepository;
import com.minivision.aop.auth.rest.param.GetTokenParam;
import com.minivision.aop.auth.rest.result.TokenResult;
import com.minivision.aop.kong.impl.KongClient;
import com.minivision.aop.kong.model.plugin.authentication.oauth2.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
@Transactional(rollbackFor = {Exception.class})
@Slf4j
@RefreshScope
public class AppServiceImpl implements AppService {
  
  @Autowired
  private AppRepository appRepo;
  
  @Autowired
  private AuthProperties properties;
  
  @Autowired
  private KongClient kongClient;
  
  @Autowired
  private OkHttpClient okhttpClient;
  
  @Autowired
  private Gson gson;
  
  @Value("${kong.oauth2.url}")
  private String kongUrl;
  
  public String createCacheKeyMethod(AppInfo appInfo) {
    return appInfo.getUsername() + "_apps";
  }

  @Override
  @HystrixCommand
  @CacheRemove(commandKey = "findAppListByUsername", cacheKeyMethod = "createCacheKeyMethod")
  public AppInfo create(@CacheKey("username") AppInfo appInfo) {
    List<String> redirectURIs = new ArrayList<>();
    redirectURIs.add(appInfo.getAppUrl());
    Application registerKongApp = new Application(appInfo.getUsername() + "_" + appInfo.getAppName(), redirectURIs);
    Application kongApp = kongClient.getOAuth2Service().createApplication(appInfo.getUsername(), registerKongApp);
    log.info("新应用被创建：" + kongApp);
    appInfo.setAppId(kongApp.getId());
    appInfo.setSecretId(kongApp.getClientId());
    appInfo.setSecretKey(kongApp.getClientSecret());
    
    AppInfo created = appRepo.saveAndFlush(appInfo);
    
    String apiGroup = appInfo.getApiGroup();
    String[] groups = apiGroup.split(",");
    for (String group : groups) {
      kongClient.getAclService().associateConsumer(appInfo.getUsername(), "group" + group);
    }
    
    return created;
  }
  
  public String updateCacheKeyMethod(AppInfo appInfo) {
    return appInfo.getUsername() + "_apps";
  }

  @Override
  @HystrixCommand
  @CacheRemove(commandKey = "findAppListByUsername", cacheKeyMethod = "updateCacheKeyMethod")
  public AppInfo update(@CacheKey("username") AppInfo appInfo) {
    AppInfo exists = appRepo.findOne(appInfo.getId());
    if (StringUtils.isNotBlank(appInfo.getAppDesc())) {
      exists.setAppDesc(appInfo.getAppDesc());
    }
    if (appInfo.getAppFunc() != 0) {
      exists.setAppFunc(appInfo.getAppFunc());
    }
    if (StringUtils.isNotBlank(appInfo.getAppPlat())) {
      exists.setAppPlat(appInfo.getAppPlat());
    }
    if (StringUtils.isNotBlank(appInfo.getCallType())) {
      exists.setCallType(appInfo.getCallType());
    }
    if (StringUtils.isNotBlank(appInfo.getApiGroup())) {
      exists.setApiGroup(appInfo.getApiGroup());
    }
    return appRepo.saveAndFlush(exists);
  }

  @Override
  @HystrixCommand
  @CacheResult(cacheKeyMethod = "findAppListByUsernameCacheKeyMethod")
  public List<AppInfo> findAppListByUsername(@CacheKey String username) {
    return appRepo.findByUsername(username);
  }
  
  public String findAppListByUsernameCacheKeyMethod(String username) {
    return username + "_apps";
  }

  @Override
  @HystrixCommand
  @CacheRemove(commandKey = "findAppListByUsername", cacheKeyMethod = "deleteCacheKeyMethod")
  public void delete(@CacheKey String username, Long id) {
    appRepo.delete(id);
  }
  
  public String deleteCacheKeyMethod(String username, Long id) {
    return username + "_apps";
  }

  @Override
  @HystrixCommand(
      threadPoolKey = "getAccessTokenPool")
  public List<TokenResult> getAccessToken(GetTokenParam param) throws IOException {
    List<TokenResult> result = new ArrayList<>();
    AppInfo appInfo = appRepo.findByAppId(param.getAppId());
    String apiGroup = appInfo.getApiGroup();
    String[] groups = apiGroup.split(",");
    FormBody requestBody = new FormBody
        .Builder()
        .add("client_id", param.getSecretID())
        .add("client_secret", param.getSecretKey())
        .add("grant_type","client_credentials")
        .build();
    for (String group : groups) {
      Request request = new Request.Builder()
              .url(kongUrl + properties.getApiGroup().get("group" + group) + "/oauth2/token")
              .post(requestBody)
              .build();

      Response response = okhttpClient.newCall(request).execute();
      TokenResult tokenResult = gson.fromJson(response.body().string(), TokenResult.class);
      tokenResult.setApiGroup(group);
      result.add(tokenResult);
    }
    return result;
  }

  @Override
  @HystrixCommand
  public AppInfo findByAppId(String appId) {
    return appRepo.findByAppId(appId);
  }

  @Override
  @HystrixCommand
  public AppInfo findByUsernameAndAppName(String username, String appName) {
    return appRepo.findByUsernameAndAppName(username, appName);
  }

}
