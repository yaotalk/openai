package com.minivision.aop.portal.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minivision.ai.rest.result.RestResult;
import com.minivision.common.domain.app.Developer;

@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private UserAuthenticationProvider userAuthenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().cors().and().rememberMe()
      .and().authorizeRequests()
      .and().formLogin()
      .successHandler((request, response, authentication) -> {
        ObjectMapper objectMapper = new ObjectMapper();  
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        Developer developer = (Developer) authentication.getPrincipal();
        byte[] writeValueAsBytes = objectMapper.writeValueAsBytes(new RestResult<>(developer));
        response.getOutputStream().write(writeValueAsBytes);
        response.getOutputStream().flush();
      }).failureHandler((request, response, exception) -> {
        ObjectMapper objectMapper = new ObjectMapper();  
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        byte[] writeValueAsBytes = objectMapper.writeValueAsBytes(new RestResult<>(exception));
        response.getOutputStream().write(writeValueAsBytes);
        response.getOutputStream().flush();
        //objectMapper.writeValue(response.getOutputStream(), new RestResult<>(exception));
      })
      .and().exceptionHandling().authenticationEntryPoint(ajaxAuthenticationEntryPoint()).accessDeniedHandler(ajaxAccessDeniedHandler())
      .and().logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).permitAll();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
      //TODO auth.userDetailsService()?
      auth.authenticationProvider(userAuthenticationProvider);
  }
  
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() 
    throws Exception {
      return super.authenticationManagerBean();
  }
    
  @Bean
  public StandardPasswordEncoder passwordEncoder(){
    return new StandardPasswordEncoder("test");
  }
  
  @Bean
  public AuthenticationEntryPoint ajaxAuthenticationEntryPoint(){
    return new AuthenticationEntryPoint() {
      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response,
          AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();  
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        RestResult<Object> restResult = new RestResult<>(authException);
        restResult.setErrorCode(401); //未鉴权
        byte[] writeValueAsBytes = objectMapper.writeValueAsBytes(restResult);
        response.getOutputStream().write(writeValueAsBytes);
        response.getOutputStream().flush();
      }
    };
  }

  @Bean
  public AccessDeniedHandler ajaxAccessDeniedHandler() {
    return new AccessDeniedHandler() {
      @Override
      public void handle(HttpServletRequest request, HttpServletResponse response,
          AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();  
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        RestResult<Object> restResult = new RestResult<>(accessDeniedException);
        restResult.setErrorCode(403); //权限不足 Forbidden
        byte[] writeValueAsBytes = objectMapper.writeValueAsBytes(restResult);
        response.getOutputStream().write(writeValueAsBytes);
        response.getOutputStream().flush();
      }
    };
  }
  
/*  public UsernamePasswordAuthenticationFilter ajaxLoginFilter() throws Exception {
    UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
    filter.setAuthenticationManager(super.authenticationManagerBean());
    return filter;
  }*/
}
