package com.lakshman.todo.auth;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.lakshman.todo.auth.AuthResponse.GoogleTokenResponse;
import com.lakshman.todo.common.configuration.GoogleAuthProperties;
import com.lakshman.todo.common.configuration.JWTProperties;
import com.lakshman.todo.common.contants.ResponseOrErrorMessages;
import com.lakshman.todo.common.contants.SystemCustomCode;
import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.utils.ResponseBuilders;
import com.lakshman.todo.contants.enums.ProviderType;
import com.lakshman.todo.contants.enums.RoleType;
import com.lakshman.todo.contants.enums.StatusType;
import com.lakshman.todo.exception.ResourceAlreadyExists;
import com.lakshman.todo.security.JWTHelper;
import com.lakshman.todo.user.UserEntity;
import com.lakshman.todo.user.UserHelper;
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
        private final GoogleAuthProperties googleAuthProperties;
        private final RestTemplate restTemplate;
        private final UserHelper UserHelper;

        @Transactional
        public ApiResponse<AuthResponse> registerUserUsingEmailAndPassword(AuthRequest authRequest,
                        HttpServletResponse response) {

                String email = authRequest.getEmail().toLowerCase();

                if (userRepository.existsByEmail(email)) {
                        throw new ResourceAlreadyExists(ResponseOrErrorMessages.USER_ALREADY_EXIST_WITH_MAIL);
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

                AuthResponse authResponse = generateTokenAndGetAuthResponse(savedUser, response);
                // send mail using AWS lambda or SES

                return ResponseBuilders.buildSuccessResponse(authResponse, ResponseOrErrorMessages.REGISTERED,
                                SystemCustomCode.REGISTERED);
        }

        @Transactional
        public ApiResponse<AuthResponse> loginWithGoogle(AuthRequest.GoogleRequest request,
                        HttpServletResponse response) {

                // Exchange code
                GoogleTokenResponse tokenResponse = exchangeCodeForToken(request.getCode());
                String idToken = tokenResponse.getId_token();
                String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
                ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

                log.info("SUccessfully got the token to get infof" + userInfoResponse);

                if (userInfoResponse.getStatusCode() == HttpStatus.OK) {

                        AuthResponse.GoogleUserInfo userDetails = extractFromUserInfoResponse(userInfoResponse);

                        log.info("User details is like t" + userDetails);

                        String email = userDetails.getEmail().toLowerCase();
                        Boolean userexistings = userRepository.existsByEmail(email);
                        UserEntity user = new UserEntity();
                        if (userexistings) {
                                user = userRepository.findUserWithAuthorities(email, RoleType.USER).get();
                                if (user.getProviderType() == null || !user.getProviderType().name()
                                                .equalsIgnoreCase(ProviderType.GOOGLE.name())) {
                                        throw new ResourceAlreadyExists(
                                                        "Account exists with different login method please try with that");
                                }
                        } else {
                                RoleEntity userRole = roleRepository.findByName(RoleType.USER)
                                                .orElseThrow(() -> new RuntimeException("USER role not found"));
                                user.setEmail(email);
                                user.setPassword(null); // No password for Google
                                user.setProviderType(ProviderType.GOOGLE);
                                user.setProviderId(userDetails.getIssuer());
                                user.setStatus(StatusType.ACTIVE); //// later set the last login
                                user.getRoles().add(userRole);
                        }
                        // these might be keep changing from use side
                        user.setFirstName(UserHelper.getFirstName(userDetails.getName()));
                        user.setProfileImage(userDetails.getPicture());
                        user.setLastName(UserHelper.getLasttName(userDetails.getName()));

                        log.info("USER data after setting  " + user);
                        userRepository.save(user);
                        // Generate your own JWT
                        AuthResponse authResponse = generateTokenAndGetAuthResponse(user, response);
                        return ResponseBuilders.buildSuccessResponse(authResponse, "Google Login Successful",
                                        SystemCustomCode.LOGGED_IN);
                }
                return ResponseBuilders.buildResponseWithErrorMessage("Something went wrong please try again");
        }

        @Transactional
        public ApiResponse<AuthResponse> loginUserUsingEmailAndPassword(LoginRequest loginRequest,
                        HttpServletResponse response) {

                String email = loginRequest.getEmail().toLowerCase();

                if (!userRepository.existsByEmail(email)) {
                        return ResponseBuilders.buildResponseWithErrorMessage(
                                        ResponseOrErrorMessages.USER_NOT_FOUND_WITH_MAIL);
                }

                // Reload with full authorities (avoids lazy issues)
                UserEntity savedUser = userRepository.findUserWithAuthorities(email, RoleType.USER)
                                .orElseThrow(() -> new RuntimeException(ResponseOrErrorMessages.USER_NOT_FOUND));

                if (!passwordEncoder.matches(loginRequest.getPassword(), savedUser.getPassword())) {
                        return ResponseBuilders
                                        .buildResponseWithErrorMessage(ResponseOrErrorMessages.INVALID_EMAIL_OR_PWD);
                }

                AuthRequest authRequest = new AuthRequest();
                authRequest.setEmail(email);
                authRequest.setPassword(loginRequest.getPassword());

                AuthResponse authResponse = generateTokenAndGetAuthResponse(savedUser, response);

                return ResponseBuilders.buildSuccessResponse(authResponse, ResponseOrErrorMessages.LOGGED_IN,
                                SystemCustomCode.LOGGED_IN);
        }

        private AuthResponse generateTokenAndGetAuthResponse(
                        UserEntity userEntity, HttpServletResponse response) {

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
                                .user(userEntity)
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

        public GoogleTokenResponse exchangeCodeForToken(String code) {
                RestTemplate restTemplate = new RestTemplate();
                MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
                requestBody.add("client_id", googleAuthProperties.getClientId());
                requestBody.add("client_secret", googleAuthProperties.getClientSecret());
                requestBody.add("code", code);
                requestBody.add("grant_type", "authorization_code");
                requestBody.add("redirect_uri", googleAuthProperties.getRedirectUri());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

                ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                                "https://oauth2.googleapis.com/token",
                                request,
                                GoogleTokenResponse.class);

                return response.getBody();
        }

        public AuthResponse.GoogleUserInfo extractFromUserInfoResponse(ResponseEntity<Map> response) {

                Map<String, Object> body = response.getBody();

                if (body == null) {
                        throw new RuntimeException("Google user info response is empty");
                }

                return AuthResponse.GoogleUserInfo.builder()
                                // .issuer() set it later
                                .subject((String) body.get("id"))
                                .email((String) body.get("email"))
                                .emailVerified((Boolean) body.get("verified_email"))
                                .name((String) body.get("name"))
                                .picture((String) body.get("picture"))
                                .build();
        }
}