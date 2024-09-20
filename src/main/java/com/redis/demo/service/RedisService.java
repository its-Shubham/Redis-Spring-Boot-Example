package com.redis.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RedisService {
  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;

  public RedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
    this.redisTemplate = redisTemplate;
    this.objectMapper = objectMapper;
  }

  // Method to get data from Redis and deserialize it
  public <T> T get(String key, Class<T> responseClass) {
    try {
      String json = redisTemplate.opsForValue().get(key);
      if (json != null) {
        json = json.replaceAll("\u0000", "");
        return objectMapper.readValue(json, responseClass);
      }
      return null;
    } catch (Exception e) {
      log.error("Exception ", e);
      return null;
    }
  }

  // Method to set data into Redis with a timeout
  public <T> void set(String key, T value, long timeoutSeconds) {
    try {
      String json = objectMapper.writeValueAsString(value);
      json = json.replaceAll("\u0000", "");
      redisTemplate.opsForValue().set(key, json, timeoutSeconds);
    } catch (Exception e) {
      log.error("Exception ", e);
    }
  }

  // Method to get a list of data from Redis and deserialize it
//  public <T> List<T> getList(String key, Class<T> elementClass) {
//    try {
//      String json = redisTemplate.opsForValue().get(key);
//      if (json != null) {
//        CollectionType collectionType = objectMapper.getTypeFactory()
//                .constructCollectionType(List.class, elementClass);
//        return objectMapper.readValue(json, collectionType);
//      }
//      return null;
//    } catch (Exception e) {
//      log.error("Exception ", e);
//      return null;
//    }
//  }
}
