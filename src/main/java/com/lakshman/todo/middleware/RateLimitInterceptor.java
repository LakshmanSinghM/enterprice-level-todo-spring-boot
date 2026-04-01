package com.lakshman.todo.middleware;

import jakarta.servlet.http.*;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lakshman.todo.common.configuration.redis.RateLimit;
import com.lakshman.todo.common.configuration.redis.RateLimiterProperties;
import com.lakshman.todo.common.configuration.redis.RedisRateLimiterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RedisRateLimiterService limiter;
    private final RateLimiterProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod method))
            return true;

        RateLimit rateLimit = method.getMethodAnnotation(RateLimit.class);

        // default from ENV
        int limit = properties.getApiLimit();
        int window = properties.getApiWindowTime();
        int waitTime = properties.getApiWaitTime();

        // override if annotation present
        // if (rateLimit != null) {
        // limit = rateLimit.limit();
        // window = rateLimit.window();
        // waitTime = rateLimit.waitTime();
        // }

        String userId = request.getHeader("X-USER-ID");

        if (userId == null)
            return true;

        String api = request.getRequestURI();

        log.info("Rate limit interceptor running: userId={}, api={}, limit={}, window={}, waitTime={}",
                userId, api, limit, window, waitTime);

        boolean allowed = limiter.allow(userId, api, limit, window, waitTime);

        if (!allowed) {
            response.setStatus(429);
            response.setHeader("Retry-After",
                    String.valueOf(waitTime > 0 ? waitTime : window));
            response.getWriter().write("Rate limit exceeded");
            return false;
        }

        return true;
    }
}