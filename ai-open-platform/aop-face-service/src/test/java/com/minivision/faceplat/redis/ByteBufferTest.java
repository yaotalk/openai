package com.minivision.faceplat.redis;

import java.nio.ByteBuffer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ByteBufferTest {
  
  @Test
  public void test(){
    ByteBuffer buffer = ByteBuffer.allocate(32*4);
    buffer.putFloat(1f);
    buffer.putFloat(2f);
    buffer.putFloat(3f);
    buffer.putFloat(4f);
    float[] array = buffer.asFloatBuffer().array();
    System.out.println(array);
  }

}
