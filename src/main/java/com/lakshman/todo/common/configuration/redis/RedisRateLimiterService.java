package com.lakshman.todo.common.configuration.redis;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisRateLimiterService {

    private final StringRedisTemplate redisTemplate;

    public boolean allow(String userId, String api, int limit, int window, int waitTime) {

        long now = Instant.now().getEpochSecond();

        // Throttle check
        if (waitTime > 0) {
            String throttleKey = "throttle:" + api + ":" + userId;
            String last = redisTemplate.opsForValue().get(throttleKey);

            if (last != null) {
                long lastTime = Long.parseLong(last);

                if (now - lastTime < waitTime) {
                    return false;
                }
            }

            redisTemplate.opsForValue().set(throttleKey, String.valueOf(now), waitTime, TimeUnit.SECONDS);
        }

        // Sliding window rate limit
        String key = "rate:" + api + ":" + userId;

        redisTemplate.opsForZSet().add(key, String.valueOf(now), now);

        redisTemplate.opsForZSet().removeRangeByScore(key, 0, now - window);

        Long count = redisTemplate.opsForZSet().zCard(key);

        redisTemplate.expire(key, window, TimeUnit.SECONDS);

        return count != null && count <= limit;
    }
}