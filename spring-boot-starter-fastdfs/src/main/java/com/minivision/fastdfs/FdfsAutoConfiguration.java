package com.minivision.fastdfs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import fastdfs.client.FastdfsClient;

@Configuration
@EnableConfigurationProperties(FdfsProperties.class)
public class FdfsAutoConfiguration {

  private final FdfsProperties properties;

  public FdfsAutoConfiguration(FdfsProperties properties) {
    super();
    this.properties = properties;
  }

  /**
   * FastDFS客户端Bean
   * @return
   */
  @Bean
  public FastdfsClient fastdfsClient() {
    FastdfsClient.Builder clientBuilder = FastdfsClient.newBuilder();
    if (properties.getConnectTimeout() > 0) {
      clientBuilder.connectTimeout(properties.getConnectTimeout());
    }
    if (properties.getReadTimeout() > 0) {
      clientBuilder.readTimeout(properties.getReadTimeout());
    }
    if (properties.getIdleTimeout() > 0) {
      clientBuilder.idleTimeout(properties.getIdleTimeout());
    }
    if (properties.getMaxThreads() > 0) {
      clientBuilder.maxThreads(properties.getMaxThreads());
    }
    if (properties.getMaxConnPerHost() > 0) {
      clientBuilder.maxConnPerHost(properties.getMaxConnPerHost());
    }
    if (!CollectionUtils.isEmpty(properties.getTrackerServers())) {
      for (String tracker : properties.getTrackerServers()) {
        String[] ipPort = tracker.split(":");
        clientBuilder.tracker(ipPort[0], Integer.parseInt(ipPort[1]));
      }
    }
    // FastdfsClient is threadsafe and use connection pool
    return clientBuilder.build();
  }

  @Bean
  public FdfsService fdfsService() {
    return new FdfsService();
  }

}
