package com.minivision.file.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件服务需要的参数配置
 * @author hughzhao
 * @2017年7月6日
 */
@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix="file")
public class FileConfig {

	// 文件访问地址Nginx前缀，Map的Key为组名，Value为该组对应的Nginx地址
	private Map<String, String> urlPrefix;
	
	// 短链接服务的根路径
	private String shortenServiceRoot;
	
	// 转换为短链接服务的请求地址
	private String shortenServiceUrl;
	
	// 还原短链接服务的请求地址
	private String expandServiceUrl;
	
}
