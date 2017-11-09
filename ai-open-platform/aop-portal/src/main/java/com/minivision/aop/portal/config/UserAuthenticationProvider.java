package com.minivision.aop.portal.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.minivision.aop.portal.service.UserService;
import com.minivision.common.domain.app.Developer;
import com.minivision.common.domain.app.Role;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider{
  
  @Autowired
  private UserService userService;
  
  @Override
  public boolean supports(Class<?> authentication) {
    //only for username and password authentication
    return UsernamePasswordAuthenticationToken.class == authentication;
  }
  
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getPrincipal().toString();
    Object credentials = authentication.getCredentials();
    String password = credentials == null ? null : credentials.toString();
    //Collection<? extends GrantedAuthority> authorities = remoteAuthenticationManager.attemptAuthentication(username, password);
    
    Developer developer = userService.checkLogin(username, password).getData();
    
    if(developer != null) {
      //TODO authority
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.getNameByIndex(developer.getRole()));
      return new UsernamePasswordAuthenticationToken(developer, password, Arrays.asList(authority));
    }else {
      //TODO classify the detail authenticationException, UsernameNotFoundException or BadCredentialsException etc.
      throw new UsernameNotFoundException("login failure");
    }
  }

  
  
}
