package com.minivision.aop.faceset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.minivision.aop.faceset.config.FeignConfig;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(defaultConfiguration = {FeignConfig.class})
@EnableHystrix
@EnableScheduling
@EnableAsync
public class App {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}
