package com.minivision.file.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.servlet.MultipartConfigElement;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import fastdfs.client.FastdfsClient;

/**
 * 文件存储需要使用到的Bean配置
 * @author hughzhao
 * @2017年7月6日
 */
@Configuration
public class FastDFSClient {
	
	@Autowired
	FastDFSConfig fastdfsConfig;
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	/**
	 * FastDFS客户端Bean
	 * @return
	 */
	@Bean
	public FastdfsClient fastdfsClient() {
		FastdfsClient.Builder clientBuilder = FastdfsClient.newBuilder();
		if (fastdfsConfig.getConnectTimeout() > 0) {
			clientBuilder.connectTimeout(fastdfsConfig.getConnectTimeout());
		}
		if (fastdfsConfig.getReadTimeout() > 0) {
			clientBuilder.readTimeout(fastdfsConfig.getReadTimeout());
		}
		if (fastdfsConfig.getIdleTimeout() > 0) {
			clientBuilder.idleTimeout(fastdfsConfig.getIdleTimeout());
		}
		if (fastdfsConfig.getMaxThreads() > 0) {
			clientBuilder.maxThreads(fastdfsConfig.getMaxThreads());
		}
		if (fastdfsConfig.getMaxConnPerHost() > 0) {
			clientBuilder.maxConnPerHost(fastdfsConfig.getMaxConnPerHost());
		}
		if (!CollectionUtils.isEmpty(fastdfsConfig.getTrackerServers())) {
			for (String tracker : fastdfsConfig.getTrackerServers()) {
				String[] ipPort = tracker.split(":");
				clientBuilder.tracker(ipPort[0], Integer.parseInt(ipPort[1]));
			}
		}
		// FastdfsClient is threadsafe and use connection pool
		return clientBuilder.build();
	}
	
	/**
	 * Rest客户端Bean
	 * @return
	 * @throws Exception
	 */
	@Bean
	public RestTemplate restTemplate() throws Exception {
		restTemplateBuilder.requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient()));
		
		List<HttpMessageConverter<?>> converterList = new ArrayList<>();
		HttpMessageConverter<?> stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		converterList.add(stringConverter);

		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaTypeList = Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON, new MediaType("application", "*+json"), new MediaType("text", "javascript")});
		jackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
		converterList.add(jackson2HttpMessageConverter);

		restTemplateBuilder.messageConverters(converterList);
		return restTemplateBuilder.build();
	}
	
	/**
	 * HTTP请求客户端Bean
	 * @return
	 * @throws Exception
	 */
	@Bean
	public HttpClient httpClient() throws Exception {
		SSLContext sslcontext =
				SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
				sslcontext, new String[] {"TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);

		Registry<ConnectionSocketFactory> socketFactoryRegistry =
				RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslConnectionSocketFactory).build();

		PoolingHttpClientConnectionManager connectionManager =
				new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(32);// 单路由最大并发数

		return HttpClients.custom().setConnectionManager(connectionManager).build();
	}
	
	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("2MB");
		return factory.createMultipartConfig();
	}
	
	@Bean
	public Gson gson() {
		return new Gson();
	}

}
