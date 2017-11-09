package com.minivision.aop.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.minivision.aop.kong.impl.KongClient;

@Configuration
@RefreshScope
public class AuthConfig {
  
  @Value("${kong.admin.url}")
  private String kongUrl;

  @Bean
  public KongClient kongClient() {
    return new KongClient(kongUrl);
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
}
