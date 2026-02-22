package com.lakshman.todo.middleware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lakshman.todo.security.JWTHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // @Autowired
    // private JWTHelper jwtHelper;

    // @Autowired
    // private CustomUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // String authHeader = request.getHeader("Authorization");
        // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        // filterChain.doFilter(request, response);
        // return;
        // }

        // String jwt = authHeader.substring(7);
        // String username = jwtHelper.extractUsername(jwt);

        // if (username != null &&
        // SecurityContextHolder.getContext().getAuthentication() == null) {

        // // Validate token validity (expiration, signature)
        // if (jwtHelper.isTokenValid(jwt)) {

        // // Extract authorities directly from token (Stateless)
        // java.util.List<String> authoritiesStr = jwtHelper.extractAuthorities(jwt);

        // java.util.List<org.springframework.security.core.authority.SimpleGrantedAuthority>
        // authorities = authoritiesStr
        // .stream()
        // .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
        // .toList();

        // // Create authentication token with user-embedded authorities
        // UsernamePasswordAuthenticationToken authenticationToken = new
        // UsernamePasswordAuthenticationToken(
        // username, null, authorities);

        // authenticationToken.setDetails(new
        // WebAuthenticationDetailsSource().buildDetails(request));
        // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // }
        // }
        filterChain.doFilter(request, response);
    }

    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest request) {
    //     return request.getServletPath().startsWith("/api/v1/auth");
    // }
}