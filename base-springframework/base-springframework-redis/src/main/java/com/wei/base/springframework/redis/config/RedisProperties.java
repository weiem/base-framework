package com.wei.base.springframework.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties implements Serializable {

    private static final long serialVersionUID = -4633090811506861386L;
    /**
     * redis锁key值
     */
    String lockKey;

    /**
     * redis锁默认过期时间
     */
    Long expirationTime;
}