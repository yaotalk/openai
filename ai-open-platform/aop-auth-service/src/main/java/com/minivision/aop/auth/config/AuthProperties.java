package com.minivision.aop.auth.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Component
@RefreshScope
@ConfigurationProperties(prefix="auth")
public class AuthProperties {

  private Map<String, String> apiGroup;
  
}
