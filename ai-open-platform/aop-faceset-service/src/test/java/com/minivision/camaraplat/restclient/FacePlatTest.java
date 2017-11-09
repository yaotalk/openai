package com.minivision.camaraplat.restclient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.minivision.aop.faceset.App;
import com.minivision.aop.faceset.faceplat.client.FacePlatClient;
import com.minivision.aop.faceset.faceplat.result.detect.faceset.SetListResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=App.class)
public class FacePlatTest {

  @Autowired
  private FacePlatClient client;
  
  @Test
  public void test(){
    
    //byte[] image = FileUtils.readFileToByteArray(new File("E://44.jpg"));
    //SearchResult search = client.search("d4f8f338-a751-4875-b044-a521712f5b03", image, 100);
    //CompareResult result = client.compare("c52c68a5-23c2-4ac8-b07d-8b1b4e5347e91", "f7426a29-bd7b-450d-ad5c-637da4cf94b7");
    SetListResult faceList = client.faceList(0, 2);
    System.out.println(ToStringBuilder.reflectionToString(faceList));
  }
}
