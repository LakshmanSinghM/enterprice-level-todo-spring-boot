package com.lakshman.todo.common.configuration.redis;


import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final StringRedisTemplate redisTemplate;

    public boolean tryLock(String key) {

        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "locked", 10, TimeUnit.SECONDS);

        return Boolean.TRUE.equals(success);
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}