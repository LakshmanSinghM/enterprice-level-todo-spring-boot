package com.lakshman.todo.common.aspect; 
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAspect.class);

    @AfterThrowing(pointcut = "execution(* com.preowendly.feature..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getMessage() != null ? ex.getMessage() : "NULL");
    }
}