package com.minivision.faceplat.redis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.minivision.aop.face.entity.Face;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private RedisTemplate<String, Face> faceTemplate;

  @Test
  public void test() throws Exception {
    
    stringRedisTemplate.opsForValue().set("test1", "1");
    stringRedisTemplate.opsForValue().set("test2", "2");
    stringRedisTemplate.opsForValue().set("test3", "3");
    stringRedisTemplate.opsForValue().set("test4", "4");
    stringRedisTemplate.opsForValue().set("test5", "5");
    stringRedisTemplate.opsForValue().set("test6", "6");
    stringRedisTemplate.opsForValue().set("test7", "7");
    stringRedisTemplate.opsForValue().set("test8", "8");
    stringRedisTemplate.opsForValue().set("test9", "9");
    stringRedisTemplate.opsForValue().set("test10", "10");
    
    List<String> keys = Arrays.asList("test123","test789","test456","test1","test2","test3","test4","test5","test6","test7","test8","test9","test10");
    List<String> multiGet = stringRedisTemplate.opsForValue().multiGet(keys);
    System.out.println(multiGet);
    Set<String> keySet = new HashSet<>();
    keySet.add("test123");
    keySet.add("test456");
    keySet.add("test789");
    keySet.add("test1");
    keySet.add("test2");
    keySet.add("test3");
    keySet.add("test4");
    keySet.add("test5");
    keySet.add("test6");
    keySet.add("test7");
    keySet.add("test8");
    keySet.add("test9");
    keySet.add("test10");
    List<String> setGet = stringRedisTemplate.opsForValue().multiGet(keySet);
    int i = 0;
    for(String k: keySet){
      System.out.println(k+" : "+setGet.get(i));
      i++;
    }
  }

  //@Test
  public void test1() throws Exception {
    Face face = new Face();
    face.setToken("1233445");
    faceTemplate.opsForValue().set("face1", face);
    Assert.assertEquals("1233445", faceTemplate.opsForValue().get("face1").getToken());
  }

}
