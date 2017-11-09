package com.minivision.faceplat.redis;

import java.util.Date;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.minivision.aop.face.entity.Face;
import com.minivision.aop.face.rest.result.faceset.SetCreateResult;
import com.minivision.aop.face.service.FaceCommonService;
import com.minivision.aop.face.service.FaceSetService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisFaceGen {

  @Autowired
  private FaceCommonService commonService;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private FaceSetService faceSetService;
  
  private Random random = new Random();

  //@Test
  public void createSet() throws Exception {
    String owner = "test",  displayName = "100000 faces";
    SetCreateResult setCreateResult = faceSetService.create(owner, null, displayName, 500000);
    System.err.println(setCreateResult.getFacesetToken());
    Assert.assertTrue(setCreateResult != null);
  }
  
  @Test
  public void genFace(){
    String setToken = "a47ec486-1fb3-4252-8677-8d7be1b015b4";
    for(int i=0;i< 10;i++){
      Face face = new Face();
      face.setFeature(genFuture());
      commonService.save(face);
      String key = commonService.getFaceSetKey(setToken);
      stringRedisTemplate.opsForZSet().add(key, face.getToken(), 0);
      
      if(i%100 == 0){
        System.err.println(new Date()+"  generate "+i+" faces..." );
      }
    }
  }
  
  private float[] genFuture(){
    float[] future = new float[128];
    for(int i=0; i<128; i++){
      future[i] = random.nextFloat() - 0.5f;
    }
    return future;
  }

}
