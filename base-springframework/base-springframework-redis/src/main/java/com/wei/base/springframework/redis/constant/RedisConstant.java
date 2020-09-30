package com.wei.base.springframework.redis.constant;

public interface RedisConstant {

    String LOCK_KEY = "LOCK:";

    String FAIR_LOCK = "FAIR_LOCK:";

    String READ_WRITE_LOCK = "READ_WRITE_LOCK:";

    String COUNT_DOWN_LATCH = "COUNT_DOWN_LATCH:";
}
