package com.lakshman.todo.middleware;

import jakarta.servlet.http.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshman.todo.common.configuration.redis.RateLimit;
import com.lakshman.todo.common.configuration.redis.RateLimiterProperties;
import com.lakshman.todo.common.configuration.redis.RedisRateLimiterService;
import com.lakshman.todo.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lakshman.todo.security.JWTHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RedisRateLimiterService limiter;
    private final RateLimiterProperties properties;
    private final JWTHelper jwtHelper;

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
        if (rateLimit != null) {
            limit = rateLimit.limit();
            window = rateLimit.window();
            waitTime = rateLimit.waitTime();
        }

        String userId = getUserIdForRateLimiting(request);
        String api = request.getRequestURI();

        log.info("Rate limit interceptor running: userId={}, api={}, limit={}, window={}, waitTime={}",
                userId, api, limit, window, waitTime);

        boolean allowed = limiter.allow(userId, api, limit, window, waitTime);

        if (!allowed) {

            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setContentType("application/json");

            int retryAfter = waitTime > 0 ? waitTime : window;
            response.setHeader("Retry-After", String.valueOf(retryAfter));
            
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.setData(null);
            apiResponse.setMessage("Rate limit exceeded. Try again after " + retryAfter + " seconds");
            apiResponse.setSuccess(false);
            apiResponse.setSystemCode("RATE_LIMIT_EXCEEDED");
            apiResponse.setHttpCode(HttpStatus.TOO_MANY_REQUESTS);

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));

            return false;
        }

        return true;
    }

    public String getUserIdForRateLimiting(HttpServletRequest request) {

        String token = jwtHelper.extractToken(request);
        String userId = null;

        try {
            userId = jwtHelper.extractUsername(token);
        } catch (Exception e) {
            log.error("Error ", e.getMessage());
        }

        if (userId == null) {
            userId = request.getHeader("X-USER-ID");
        }

        if (userId == null) {
            userId = request.getHeader("X-Forwarded-For");
        }

        if (userId == null) {
            userId = request.getRemoteAddr();
        }

        userId = "rate_limit:userId:" + userId;

        return userId;
    }
}