package com.lakshman.todo.auth;

import java.security.Permission;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lakshman.todo.common.configuration.JWTProperties;
import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.utils.ResponseBuilders;
import com.lakshman.todo.contants.enums.RoleType;
import com.lakshman.todo.security.JWTHelper;
import com.lakshman.todo.user.UserEntity;
import com.lakshman.todo.user.UserRepository;
import com.lakshman.todo.user.role.RoleEntity;
import com.lakshman.todo.user.role.RoleRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;
    private final JWTProperties jwtProperties;

    @Transactional
    public ApiResponse<AuthResponse> registerUserUsingEmailAndPassword(AuthRequest authRequest,
            HttpServletResponse response) {

        if (userRepository.existsByEmail(authRequest.getEmail().toLowerCase())) {
            return ResponseBuilders.buildResponseWithErrorMessage("User already exists with this email");
        }

        // UserEntity userEntity = new UserEntity();
        // userEntity.setEmail(authRequest.getEmail());
        // userEntity.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        // userRepository.save(userEntity);

        // RoleEntity roleEntity = roleRepository.findByName(RoleType.USER).get();

        // AuthResponse authResponse = generateTokenAndGetAuthResponse(userEntity, response, authRequest);

        return ResponseBuilders.buildSuccessResponse(null, "User registered successfully");
    }

    private AuthResponse generateTokenAndGetAuthResponse(
            UserEntity userEntity, HttpServletResponse response,
            AuthRequest authRequest) {

        // Generate tokens
        // String email, Long userId, List<String> roles, List<String> permissions
        String accessToken = jwtHelper.generateAccessToken(
                authRequest.getEmail().toLowerCase(),
                userEntity.getId(), Set.of(RoleType.USER.name()), Set.of());

        String refreshToken = jwtHelper.generateRefreshToken(authRequest.getEmail().toLowerCase(), userEntity.getId());

        // Send tokens in cookies
        saveTokenInCookie(response, "accessToken", accessToken, jwtProperties.getAccessTokenExpiration());
        saveTokenInCookie(response, "refreshToken", refreshToken, jwtProperties.getRefreshTokenExpiration());

        // Prepare response body
        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roles(userEntity.getRoles()
                        .stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()))
                .permissions(Set.of())
                // .permissions(userEntity.getPermissions()
                // .stream()
                // .map(Permission::getName)
                // .toList())
                .build();
        return authResponse;
    }

    private void saveTokenInCookie(HttpServletResponse response,
            String name,
            String token,
            long maxAgeSeconds) {

        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true); // prevent JS access
        // cookie.setSecure(true); // HTTPS only (must in prod)
        cookie.setPath("/");
        cookie.setMaxAge((int) maxAgeSeconds);
        cookie.setAttribute("SameSite", "Strict"); // CSRF protection
        response.addCookie(cookie);
    }
}