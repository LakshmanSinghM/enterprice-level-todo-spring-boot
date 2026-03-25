package com.lakshman.todo.common.configuration.redis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final StringRedisTemplate redisTemplate;

    public String tryLock(String key) {
        String value = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, 10, TimeUnit.SECONDS);

        return Boolean.TRUE.equals(success) ? value : null;
    }

    public void unlock(String key, String value) {
        String currentValue = redisTemplate.opsForValue().get(key);

        if (value.equals(currentValue)) {
            redisTemplate.delete(key);
        }
    }
}