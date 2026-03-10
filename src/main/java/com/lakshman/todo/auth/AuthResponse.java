package com.lakshman.todo.auth;

import com.lakshman.todo.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private UserEntity user;

    @Data
    public static class GoogleTokenResponse {
        private String access_token;
        private String id_token;
        private String refresh_token;
        private Long expires_in;
    }

    @Data
    @Builder
    @ToString
    static class GoogleUserInfo {
        private String issuer;
        private String audience;
        private String subject;
        private String email;
        private Boolean emailVerified;
        private Long issuedAt;
        private Long expiration;
        private String name;
        private String picture;
    }

    // These two things will be set in the access token itself then decode on the FE
    // and use
    // private Set<String> roles;
    // private Set<String> permissions;
}