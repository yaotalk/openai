package com.minivision.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(InfluxDB.class)
@EnableConfigurationProperties(InfluxdbProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class InfluxdbAutoConfiguration {

  @Autowired
  private InfluxdbProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public InfluxDB influxDB() {
    return InfluxDBFactory.connect(properties.getUrl(), properties.getUsername(), properties.getPassword());
  }

}