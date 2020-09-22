package com.wei.base.springframework.redis.utils;

import com.wei.base.springframework.redis.config.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisLock<T> {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 可重入锁
     *
     * @param key            锁key值
     * @param expirationTime 过期时间
     * @param timeUnit       时间单位
     * @return 锁对象, 解锁方式  RLock.unlock()
     */
    public RLock lock(String key, long expirationTime, TimeUnit timeUnit) {
        RLock rLock = redissonClient.getLock(redisProperties.getLockKey() + key);
        rLock.lock(expirationTime, timeUnit);
        return rLock;
    }

    /**
     * 可重入锁(过期时间30秒)
     *
     * @param key 锁key值
     * @return 锁对象, 解锁方式  RLock.unlock()
     */
    public RLock lock(String key) {
        return lock(key, redisProperties.getExpirationTime(), TimeUnit.SECONDS);
    }

    /**
     * 可重入锁
     *
     * @param key            锁key值
     * @param expirationTime 过期时间(单位:秒)
     * @return 锁对象, 解锁方式  RLock.unlock()
     */
    public RLock lock(String key, long expirationTime) {
        return lock(key, expirationTime, TimeUnit.SECONDS);
    }

    /**
     * 尝试获取锁
     * <p>
     * try {
     * if(tryLock(key)) {
     * <p>
     * }
     * } catch (Exception e) {
     * } finally {
     * // 无论如何, 最后都要解锁
     * unlock();
     * }
     *
     * @param rLock     当前锁对象
     * @param waitTime  等待时间
     * @param leaseTime 过期时间
     * @param unit      时间单位
     * @return true获取成功, false获取失败
     * @throws InterruptedException 如果线程中断
     */
    public Boolean tryLock(RLock rLock, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return rLock.tryLock(waitTime, leaseTime, unit);
    }

    /**
     * 尝试获取锁
     * <p>
     * try {
     * if(tryLock(key)) {
     * <p>
     * }
     * } catch (Exception e) {
     * } finally {
     * // 无论如何, 最后都要解锁
     * unlock();
     * }
     *
     * @param rLock 当前锁对象
     * @return true获取成功, false获取失败
     * @throws InterruptedException 如果线程中断
     */
    public Boolean tryLock(RLock rLock) throws InterruptedException {
        return tryLock(rLock, redisProperties.getWaitTime(), redisProperties.getExpirationTime(), TimeUnit.SECONDS);
    }
}