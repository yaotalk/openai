package com.minivision.urlshorten;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;

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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Configuration
@EnableConfigurationProperties(UrlShortenProperties.class)
public class UrlShortenAutoConfiguration {
  
  @Autowired
  RestTemplateBuilder restTemplateBuilder;
  
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
  
  @Bean
  public Gson gson() {
      return new Gson();
  }

  @Bean
  public UrlShortenService urlShortenService() {
    return new UrlShortenService();
  }

}
