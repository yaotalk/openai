package com.minivision.aop.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.minivision.aop.content.config.CustomConfigServicePropertySourceLocator;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableHystrix
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  @Primary
  public CustomConfigServicePropertySourceLocator configServicePropertySource(ConfigClientProperties properties) {
    CustomConfigServicePropertySourceLocator locator = new CustomConfigServicePropertySourceLocator(
        properties);
    return locator;
  }
  
}
