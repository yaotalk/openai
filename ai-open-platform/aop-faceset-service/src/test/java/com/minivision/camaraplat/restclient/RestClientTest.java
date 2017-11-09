package com.minivision.camaraplat.restclient;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.minivision.aop.faceset.faceplat.client.RestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestClientTest {
  @Autowired
  private RestClient client;
  
  @Test
  public void testRestClient() throws IOException{
    String url = "http://localhost:8081/api/v1/detect";
    MultiValueMap<String, Object> mmap = new LinkedMultiValueMap<>();
    
    byte[] byteArray = FileUtils.readFileToByteArray(new File("E://14.jpg"));
    
    ByteArrayResource arrayResource = new ByteArrayResource(byteArray){ 
      @Override
      public String getFilename() throws IllegalStateException { 
          return "temp.jpg";
      }
    }; 
    
    mmap.add("imageFile", arrayResource);
    mmap.add("apiKey", "test");
    mmap.add("apiSecret", "test");
    String form = client.post(url, mmap);
    System.err.println(form);
  }
}
