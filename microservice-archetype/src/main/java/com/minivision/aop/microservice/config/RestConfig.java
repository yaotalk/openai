package com.minivision.aop.microservice.config;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

  @Bean
  public RestTemplate restTemplate() throws Exception {
      RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient()));
      
      HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
      List<HttpMessageConverter<?>> convertList = new ArrayList<>();
      convertList.add(converter);
      
      MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
      List<MediaType> asList = Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON, new MediaType("application", "*+json"), new MediaType("text", "javascript")});
      jackson2HttpMessageConverter.setSupportedMediaTypes(asList);
      convertList.add(jackson2HttpMessageConverter);
      
      template.setMessageConverters(convertList);
      
      return template;
  }
  
  private HttpClient httpClient() throws Exception {
    SSLContext sslcontext =
        SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
    SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
        sslcontext, new String[] {"TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);

    Registry<ConnectionSocketFactory> socketFactoryRegistry =
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", sslConnectionSocketFactory).build();

    PoolingHttpClientConnectionManager cm =
        new PoolingHttpClientConnectionManager(socketFactoryRegistry);
    cm.setMaxTotal(32);
    cm.setDefaultMaxPerRoute(32);// 单路由最大并发数

    return HttpClients.custom().setConnectionManager(cm).build();
  }

}
