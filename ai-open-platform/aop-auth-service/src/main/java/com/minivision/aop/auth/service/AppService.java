package com.minivision.aop.auth.service;

import java.io.IOException;
import java.util.List;

import com.minivision.aop.auth.domain.AppInfo;
import com.minivision.aop.auth.rest.param.GetTokenParam;
import com.minivision.aop.auth.rest.result.TokenResult;

public interface AppService {

  AppInfo create(AppInfo appInfo);
  AppInfo update(AppInfo appInfo);
  void delete(String username, Long id);
  List<AppInfo> findAppListByUsername(String username);
  AppInfo findByAppId(String appId);
  
  AppInfo findByUsernameAndAppName(String username, String appName);
  
  List<TokenResult> getAccessToken(GetTokenParam param) throws IOException;
  
}
