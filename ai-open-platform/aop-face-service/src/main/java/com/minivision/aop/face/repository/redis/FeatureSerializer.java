package com.minivision.aop.face.repository.redis;

import java.nio.ByteBuffer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class FeatureSerializer implements RedisSerializer<float[]> {
  
  @Override
  public byte[] serialize(float[] feature) throws SerializationException {
    if(feature == null){
      return null;
    }
    ByteBuffer buffer = ByteBuffer.allocate(feature.length * 4);
    for(float f: feature){
      buffer.putFloat(f);
    }
    return buffer.array();
  }

  @Override
  public float[] deserialize(byte[] bytes) throws SerializationException {
    if(bytes == null){
      return null;
    }
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    int size = bytes.length / 4;
    
    float[] fs = new float[size];
    for(int i=0;i<size;i++){
      fs[i] = buffer.getFloat();
    }
    return fs;
  }
  
  
}
