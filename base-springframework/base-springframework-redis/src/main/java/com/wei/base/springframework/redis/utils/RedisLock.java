package com.wei.base.springframework.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 加锁
     *
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public Boolean lock(String key, String value) {
        redisTemplate.opsForValue();

//        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {//相当于SETNX，setIfAbsent方法设置了为true,没有设置为false
//            return Boolean.TRUE;
//        }
//        redisTemplate.opsForValue().setIf
//        //假设currentValue=A   接下来并发进来的两个线程的value都是B  其中一个线程拿到锁,除非从始至终所有都是在并发（实际上这中情况是不存在的），只要开始时有数据有先后顺序，则分布式锁就不会出现“多卖”的现象
//        String currentValue = String.valueOf(redisTemplate.opsForValue().get(key));
//        //如果锁过期  解决死锁
//        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
//            //获取上一个锁的时间，锁过期后，GETSET将原来的锁替换成新锁
//            String oldValue = String.valueOf(redisTemplate.opsForValue().getAndSet(key, value));
//            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
//                return Boolean.TRUE;
//            }
//        }

        return Boolean.FALSE;//拿到锁的就有执行权力，拿不到的只有重新再来，重新再来只得是让用户手动继续抢单
    }

    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
//        try {
//            String currentValue = String.valueOf(redisTemplate.opsForValue().get(key));
//            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
//                redisTemplate.opsForValue().getOperations().delete(key);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}