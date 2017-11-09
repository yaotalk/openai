package com.minivision.common.domain.app;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AppInfo{
  private long id;
  private String username;
  private String appId;
  private String secretId;
  private String secretKey;
  private String appName;
  private String appDesc;
  private int appFunc;
  private String appUrl;
  private String appPlat;
  private String callType;
  private String apiGroup;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
}
