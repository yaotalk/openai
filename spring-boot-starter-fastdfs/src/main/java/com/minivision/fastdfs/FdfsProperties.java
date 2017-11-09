package com.minivision.fastdfs;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "fastdfs")
public class FdfsProperties {

  //连接超时时间(毫秒)
  private long connectTimeout;

  // 读超时时间(毫秒)
  private long readTimeout;

  // 连接闲置时间(毫秒)
  private long idleTimeout;

  // 线程数量
  private int maxThreads;

  // 每个IP最大连接数
  private int maxConnPerHost;

  // FastDFS Tracker Server地址，多个以英文逗号分隔，格式为“IP地址:端口”
  private List<String> trackerServers;

  // 文件访问地址Nginx前缀，Map的Key为组名，Value为该组对应的Nginx地址
  private Map<String, String> urlPrefix;

}
