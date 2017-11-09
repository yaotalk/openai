package com.minivision.aop.gateway;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.client.RestTemplate;

import com.minivision.aop.gateway.fallback.DefaultFallbackProvider;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class App {

  public static void main(String[] args) throws IOException {
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    String groovyPath = "file:" + System.getProperty("user.dir") + "/filters/*.groovy";
    Resource[] resources = resolver.getResources(groovyPath);
    if (ArrayUtils.isNotEmpty(resources)) {
      List<Object> list = Arrays.asList(resources);
      Object[] sources = new Object[list.size() + 1];
      list.toArray(sources);
      sources[sources.length - 1] = App.class;
      SpringApplication.run(sources, args);
    }
    else {
      SpringApplication.run(App.class, args);
    }
  }

  @Bean
  @LoadBalanced
  RestTemplate loadBalancedRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public ZuulFallbackProvider defaultFallbackProvider() {
    return new DefaultFallbackProvider();
  }

}
