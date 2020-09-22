package com.wei.base.springframework.redis.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LockContent implements Serializable {

    private static final long serialVersionUID = 1923389751075205531L;

    /**
     * 锁Id
     */
    private String lockId;

    /**
     * 锁超时时间
     */
    private Long lockExpirationTime;
}