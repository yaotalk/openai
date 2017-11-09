package com.minivision.aop.auth.config;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

@Configuration
public class RestConfig {

  @Autowired
  RestTemplateBuilder restTemplateBuilder;

  @Bean
  public RestTemplate restTemplate() throws Exception {
    restTemplateBuilder.requestFactory(new OkHttp3ClientHttpRequestFactory(okhttpClient()));

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

  @Bean
  public OkHttpClient okhttpClient() {
    X509TrustManager xtm = new X509TrustManager() {
      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
      }
    };

    SSLContext sslContext = null;
    try {
      sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    }
    HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
      @Override
      public boolean verify(String hostname, SSLSession session) {
        return true;
      }
    };

    return new OkHttpClient.Builder()
        .sslSocketFactory(sslContext.getSocketFactory(), xtm)
        .hostnameVerifier(DO_NOT_VERIFY)
        .build();
  }
  
  @Bean
  public Gson gson() {
      return new Gson();
  }

}
