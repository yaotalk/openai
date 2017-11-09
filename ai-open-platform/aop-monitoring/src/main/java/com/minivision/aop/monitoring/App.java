package com.minivision.aop.monitoring;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.metrics.atlas.AtlasTagProvider;
/*import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.codecentric.boot.admin.config.EnableAdminServer;
import de.codecentric.boot.admin.notify.LoggingNotifier;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
//@EnableAtlas
/*@EnableTurbineStream
@EnableHystrixDashboard*/
public class App {
  
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  AtlasTagProvider atlasCommonTags(@Value("${spring.application.name}") String appName) {
    return () -> Collections.singletonMap("app", appName);
  }

  @Configuration
  public static class NotifierConfig {

    @Bean
    @Primary
    public RemindingNotifier remindingNotifier() {
      RemindingNotifier notifier = new RemindingNotifier(filteringNotifier(loggerNotifier()));
      notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(10));
      return notifier;
    }

    @Scheduled(fixedRate = 1_000L)
    public void remind() {
      remindingNotifier().sendReminders();
    }

    @Bean
    public FilteringNotifier filteringNotifier(Notifier delegate) {
      return new FilteringNotifier(delegate);
    }

    @Bean
    public LoggingNotifier loggerNotifier() {
      return new LoggingNotifier();
    }

  }

  @Configuration
  public static class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Value("${admin.server.username}")
    private String username;
    
    @Value("${admin.server.password}")
    private String password;
    
    @Value("${admin.server.userrole}")
    private String userrole;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      // Page with login form is served as /login.html and does a POST on /login
      http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
      // The UI does a POST on /logout on logout
      http.logout().logoutUrl("/logout");
      // The ui currently doesn't support csrf
      http.csrf().disable();

      // Requests for the login page and the static assets are allowed
      http.authorizeRequests()
      .antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
      .permitAll();
      // ... and any other request needs to be authorized
      http.authorizeRequests().antMatchers("/**").authenticated();

      // Enable so that the clients can authenticate via HTTP basic for registering
      http.httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth
      .inMemoryAuthentication()
      .withUser(username).password(password).roles(userrole);
    }

  }

}
