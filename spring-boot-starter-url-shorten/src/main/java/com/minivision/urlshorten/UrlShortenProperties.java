package com.minivision.urlshorten;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "urlshorten")
public class UrlShortenProperties {

  //短链接服务的根路径
  private String shortenServiceRoot;
  
  //转换为短链接服务的请求地址
  private String shortenServiceUrl;
  
  //还原短链接服务的请求地址
  private String expandServiceUrl;
  
  //短链接请求前缀
  private String shortUrlPrefix;

}
