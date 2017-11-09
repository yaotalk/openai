package com.minivision.influxdb;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = InfluxdbProperties.INFLUXDB_PREFIX)
public class InfluxdbProperties {

    public static final String INFLUXDB_PREFIX = "spring.influxdb";

    private String url;
    private String username;
    private String password;
	
}