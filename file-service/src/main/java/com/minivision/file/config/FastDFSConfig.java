package com.minivision.file.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * FastDFS客户端参数配置
 * @author hughzhao
 * @2017年7月6日
 */
@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix="fastdfs")
public class FastDFSConfig {

	// 连接超时时间(毫秒)
	private long connectTimeout;
	
	// 读超时时间(毫秒)
	private long readTimeout;
	
	// 连接闲置时间(毫秒)
	private long idleTimeout;
	
	// 线程数量
	private int maxThreads;
	
	// 每个IP最大连接数
	private int maxConnPerHost;
	
	// FastDFS Tracker Server地址，多个以英文逗号分隔，格式为“IP地址:端口”
	private List<String> trackerServers;
	
}
