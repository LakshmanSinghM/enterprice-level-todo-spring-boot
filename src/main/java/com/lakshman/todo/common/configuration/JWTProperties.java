package com.lakshman.todo.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    private String secret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
    private String issuer;
}

// use @Validated and do the validation on the fields if config is missing then app will fail at startup only