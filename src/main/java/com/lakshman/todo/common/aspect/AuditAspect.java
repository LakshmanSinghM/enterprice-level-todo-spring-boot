package com.lakshman.todo.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

//    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    // Audit all refresh-token operations
    // @AfterReturning("execution(* com.preowendly.feature.auth.AuthService.refreshAccessToken(..))")
    // public void auditTokenRefresh() {
    //     log.info("🔐 Access token refreshed successfully (Audit Log)");
    // }
}