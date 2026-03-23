package com.lakshman.todo.common.configuration.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ratelimit")
public class RateLimiterProperties {
    private Integer apiLimit;
    private Integer apiWindowTime;
    private Integer apiWaitTime;
}