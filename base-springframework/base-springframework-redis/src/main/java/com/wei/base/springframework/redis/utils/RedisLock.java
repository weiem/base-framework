package com.wei.base.springframework.redis.utils;

import com.wei.base.springframework.redis.config.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock<T> {

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 锁
     *
     * @param key            锁key值
     * @param expirationTime 过期时间
     * @param timeUnit       时间单位
     * @return true枷锁成功, false枷锁失败
     */
    public Boolean lock(String key, Long expirationTime, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(redisProperties.getLockKey() + key, (T) "", expirationTime, timeUnit);
    }

    /**
     * 锁(过期时间30秒)
     *
     * @param key 锁key值
     * @return true枷锁成功, false枷锁失败
     */
    public Boolean lock(String key) {
        return lock(key, redisProperties.getExpirationTime(), TimeUnit.SECONDS);
    }

    /**
     * 锁
     *
     * @param key            锁key值
     * @param expirationTime 过期时间(单位:秒)
     * @return true枷锁成功, false枷锁失败
     */
    public Boolean lock(String key, Long expirationTime) {
        return lock(key, expirationTime, TimeUnit.SECONDS);
    }

    /**
     * 解锁
     *
     * @param key 锁key值
     * @return true:成功,false:失败
     */
    public Boolean unlock(String key) {
        return redisTemplate.opsForValue().getOperations().delete(redisProperties.getLockKey() + key);
    }
}