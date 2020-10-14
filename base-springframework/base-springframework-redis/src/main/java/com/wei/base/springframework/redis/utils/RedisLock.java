package com.wei.base.springframework.redis.utils;

import com.wei.base.springframework.redis.constant.RedisConstant;
import com.wei.base.springframework.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnBean(RedissonClient.class)
@Component
public class RedisLock<T> {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 可重入锁
     *
     * @param key 锁key值
     * @return 锁对象
     */
    public RLock lock(String key) {
        return redissonClient.getLock(RedisConstant.LOCK_KEY + StringUtil.upperCase(key));
    }

    /**
     * 公平锁
     *
     * @param key 锁key值
     * @return 锁对象
     */
    public RLock fairLock(String key) {
        return redissonClient.getFairLock(RedisConstant.FAIR_LOCK + StringUtil.upperCase(key));
    }

    /**
     * 读写锁 分布式可重入读写锁允许同时有多个读锁和一个写锁处于加锁状态。
     *
     * @param key 锁key值
     * @return 锁对象
     */
    public RReadWriteLock readWriteLock(String key) {
        return redissonClient.getReadWriteLock(RedisConstant.READ_WRITE_LOCK + StringUtil.upperCase(key));
    }

    /**
     * 闭锁
     * 通过trySetCount设置大小
     * 通过在其他线程或者MQ里只想countDown()执行-1操作
     * 当设置的trySetCount大小等于0时代表已经解锁
     *
     * @param key 锁key值
     * @return 锁对象
     */
    public RCountDownLatch countDownLatch(String key) {
        return redissonClient.getCountDownLatch(RedisConstant.COUNT_DOWN_LATCH + StringUtil.upperCase(key));
    }
}