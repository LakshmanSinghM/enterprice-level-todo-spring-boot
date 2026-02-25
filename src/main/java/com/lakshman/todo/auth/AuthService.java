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
import com.lakshman.todo.contants.enums.ProviderType;
import com.lakshman.todo.contants.enums.RoleType;
import com.lakshman.todo.contants.enums.StatusType;
import com.lakshman.todo.security.JWTHelper;
import com.lakshman.todo.user.UserEntity;
import com.lakshman.todo.user.UserRepository;
import com.lakshman.todo.user.role.RoleEntity;
import com.lakshman.todo.user.role.RoleRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final JWTHelper jwtHelper;
        private final JWTProperties jwtProperties;

        @Transactional
        public ApiResponse<AuthResponse> registerUserUsingEmailAndPassword(AuthRequest authRequest,
                        HttpServletResponse response) {

                String email = authRequest.getEmail().toLowerCase();

                if (userRepository.existsByEmail(email)) {
                        return ResponseBuilders.buildResponseWithErrorMessage("User already exists with this email");
                }

                RoleEntity userRole = roleRepository.findByName(RoleType.USER)
                                .orElseThrow(() -> new RuntimeException("Default USER role not found"));

                UserEntity userEntity = new UserEntity();
                userEntity.setEmail(email);
                userEntity.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                userEntity.getRoles().add(userRole);
                userEntity.setStatus(StatusType.ACTIVE);
                userEntity.setProviderType(ProviderType.LOCAL);
                userEntity.setProviderId("LocalProviderId");
                // userEntity.getCreatedBy()// change to string type, updatedBy also

                userRepository.save(userEntity);
                // Reload with full authorities (avoids lazy issues)
                UserEntity savedUser = userRepository.findUserWithAuthorities(email, RoleType.USER)
                                .orElseThrow(() -> new RuntimeException("User not found after save"));

                AuthResponse authResponse = generateTokenAndGetAuthResponse(savedUser, response, authRequest);

                return ResponseBuilders.buildSuccessResponse(authResponse, "User registered successfully");
        }

        @Transactional
        public ApiResponse<AuthResponse> loginUserUsingEmailAndPassword(LoginRequest loginRequest,
                        HttpServletResponse response) {

                String email = loginRequest.getEmail().toLowerCase();

                if (!userRepository.existsByEmail(email)) {
                        return ResponseBuilders.buildResponseWithErrorMessage("User does not exists with this email");
                }

                // Reload with full authorities (avoids lazy issues)
                UserEntity savedUser = userRepository.findUserWithAuthorities(email, RoleType.USER)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                if (!passwordEncoder.matches(loginRequest.getPassword(), savedUser.getPassword())) {
                        return ResponseBuilders.buildResponseWithErrorMessage("Invalid email or password");
                }

                AuthRequest authRequest = new AuthRequest();
                authRequest.setEmail(email);
                authRequest.setPassword(loginRequest.getPassword());

                AuthResponse authResponse = generateTokenAndGetAuthResponse(savedUser, response, authRequest);

                return ResponseBuilders.buildSuccessResponse(authResponse, "User logged in successfully");
        }

        private AuthResponse generateTokenAndGetAuthResponse(
                        UserEntity userEntity, HttpServletResponse response,
                        AuthRequest authRequest) {

                Set<String> roles = userEntity.getRoles()
                                .stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet());

                Set<String> permissions = getEffectivePermissions(userEntity);

                // Generate tokens
                String accessToken = jwtHelper.generateAccessToken(
                                userEntity.getEmail(),
                                userEntity.getId(),
                                roles,
                                permissions);

                String refreshToken = jwtHelper.generateRefreshToken(
                                userEntity.getEmail(),
                                userEntity.getId());

                // Send tokens in cookies

                saveTokenInCookie(response, "accessToken", accessToken, jwtProperties.getAccessTokenExpiration());
                saveTokenInCookie(response, "refreshToken", refreshToken, jwtProperties.getRefreshTokenExpiration());

                // Prepare response body
                return AuthResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .build();
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

        private Set<String> getEffectivePermissions(UserEntity user) {
                Set<String> permissions = new HashSet<>();

                log.info("The user permissions are coming like this  " + user);
                log.info("The user permissions are coming like this  " + user.getRoles());

                // Role permissions
                user.getRoles().forEach(
                                role -> role.getPermissions()
                                                .forEach(permission -> permissions.add(permission.getName())));

                // Direct permissions
                user.getDirectPermissions().forEach(permission -> permissions.add(permission.getName()));

                return permissions;
        }
}