package com.lakshman.todo.common.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for all controllers
    // @Around("execution(* com.preowendly..*(..))")
    // public Object logControllerMethods(ProceedingJoinPoint joinPoint,
    // RestController restController) throws Throwable {
    // long start = System.currentTimeMillis();
    // String methodName = joinPoint.getSignature().toShortString();

    // log.info("Entering: {}", methodName);
    // Object result;
    // try {
    // result = joinPoint.proceed();
    // } catch (Throwable ex) {
    // log.error("Exception in {}: {}", methodName, ex.getMessage());
    // throw ex;
    // }
    // long duration = System.currentTimeMillis() - start;
    // log.info("Exiting: {} | Time taken: {} ms", methodName, duration);
    // return result;
    // }

    // Apply logging to all layers: Controller, Service, Repository under feature
    // packages
    @Around("within(* com.lakshman.todo..controller..*(..)) || " +
            "within(* com.lakshman.todo..service..*(..)) || " +
            "within(* com.lakshman.todo..repository..*(..)) || " +
            "within(* com.lakshman.todo..*(..))")
    public Object logAllLayers(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Entering [{}@{}] with args: {}", className, methodName, Arrays.toString(joinPoint.getArgs()));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("Exception in [{}@{}]: {}", className, methodName, ex.getMessage(), ex);
            throw ex;
        }

        long duration = System.currentTimeMillis() - start;
        log.info("Exiting [{}@{}] | Time taken: {} ms", className, methodName, duration);

        return result;
    }
}