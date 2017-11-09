package com.minivision.aop.auth.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minivision.aop.auth.domain.Developer;
import com.minivision.aop.auth.enumeration.Role;
import com.minivision.aop.auth.repository.DeveloperRepository;
import com.minivision.aop.auth.rest.result.LoginResult;
import com.minivision.aop.kong.impl.KongClient;
import com.minivision.aop.kong.model.admin.consumer.Consumer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;

@Service
@Transactional(rollbackFor = {Exception.class})
public class DeveloperServiceImpl implements DeveloperService {
  
  @Autowired
  private DeveloperRepository developerRepo;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Autowired
  private KongClient kongClient;

  @Override
  @HystrixCommand
  public Developer create(Developer developer) {
    developer.setPassword(passwordEncoder.encode(developer.getPassword()));
    developer.setRole(Role.ROLE_USER.getIndex());
    
    Developer created = developerRepo.saveAndFlush(developer);
    
    Consumer consumer = new Consumer();
    consumer.setUsername(created.getUsername());
    consumer.setCustomId(created.getUsername());
    kongClient.getConsumerService().createConsumer(consumer);
    return created;
  }

  @Override
  @HystrixCommand
  @CacheRemove(commandKey = "findByUsername")
  public Developer update(@CacheKey("username") Developer developer) {
    Developer exists = developerRepo.findByUsername(developer.getUsername());
    if (StringUtils.isNotBlank(developer.getPassword())) {
      exists.setPassword(passwordEncoder.encode(developer.getPassword()));
    }
    if (StringUtils.isNotBlank(developer.getEmail())) {
      exists.setEmail(developer.getEmail());
    }
    return developerRepo.saveAndFlush(exists);
  }

  @Override
  @HystrixCommand
  @CacheResult
  public Developer findByUsername(@CacheKey String username) {
    return developerRepo.findByUsername(username);
  }

  @Override
  @HystrixCommand
  public LoginResult login(Developer developer) {
    LoginResult result = new LoginResult();
    Developer match = developerRepo.findByUsername(developer.getUsername());
    if (match == null) {
      result.setMessage("用户名不存在");
      return result;
    } 
    if (!passwordEncoder.matches(developer.getPassword(), match.getPassword())) {
      result.setMessage("密码错误");
      return result;
    } 
    
    result.setDeveloper(match);
    result.setMessage("登录成功");
    return result;
  }

}
