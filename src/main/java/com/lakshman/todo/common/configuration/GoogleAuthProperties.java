package com.lakshman.todo.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "google")
public class GoogleAuthProperties {
    private String clientSecret;
    private String clientId;
    private String redirectUri;
}