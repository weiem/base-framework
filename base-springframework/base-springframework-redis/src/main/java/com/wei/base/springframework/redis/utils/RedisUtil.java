package com.wei.base.springframework.redis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T get(String key, Class<T> t) {
        Object object = redisTemplate.opsForValue().get(key);
        return objectMapper.convertValue(object, t);
    }

    public void set(String key, Object o) {
        redisTemplate.opsForValue().set(key, o);
    }

    public void set(String key, Object o, Long timeOut) {
        if (timeOut > 0) {
            redisTemplate.opsForValue().set(key, o, timeOut, TimeUnit.SECONDS);
            return;
        }

        redisTemplate.opsForValue().set(key, o);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Map<String, Object> get(String key, String hashKey) {
        return (Map<String, Object>) redisTemplate.opsForHash().get(key, hashKey);
    }
}
