package com.lakshman.todo.security;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.lakshman.todo.common.configuration.JWTProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTHelper {

    private final JWTProperties jwtProperties;
    private SecretKey key;

    public JWTHelper(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateAccessToken(String email, Long userId, Set<String> roles, Set<String> permissions) {
        // logger here
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .claim("id", userId)
                .claim("tokenType", "ACCESS")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email, Long id) {
        // logger here
        return Jwts.builder()
                .setSubject(email)
                .claim("tokenType", "REFRESH")
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // public String extractUsername(String token) {
    // return extractClaim(token, Claims::getSubject);
    // }

    // public List<String> extractRoles(String token) {
    // return extractAllClaims(token).get("roles", List.class);
    // }

    // public Date extractExpiration(String token) {
    // return extractClaim(token, Claims::getExpiration);
    // }

    // public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    // final Claims claims = extractAllClaims(token);
    // return claimsResolver.apply(claims);
    // }

    // public boolean validateToken(String token) {
    // try {
    // extractAllClaims(token);
    // return !isTokenExpired(token);
    // } catch (JwtException | IllegalArgumentException e) {
    // return false;
    // }
    // }

    // private boolean isTokenExpired(String token) {
    // return extractExpiration(token).before(new Date());
    // }

    // private Claims extractAllClaims(String token) {
    // return Jwts.parserBuilder()
    // .setSigningKey(key)
    // .build()
    // .parseClaimsJws(token)
    // .getBody();
    // }
}