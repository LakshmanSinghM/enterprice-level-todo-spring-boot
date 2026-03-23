package com.lakshman.todo.common.configuration.redis;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisCacheConfig {

    public final Long DEFAULT_DURATION_OF_CACHE_TO_LIVE = 1l;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {

        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofDays(DEFAULT_DURATION_OF_CACHE_TO_LIVE))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }
}