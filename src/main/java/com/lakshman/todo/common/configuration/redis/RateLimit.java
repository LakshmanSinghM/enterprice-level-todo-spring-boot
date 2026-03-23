package com.lakshman.todo.common.configuration.redis;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    int limit() default 100;

    int window() default 60;

    int waitTime() default 0; // seconds gap between requests
}