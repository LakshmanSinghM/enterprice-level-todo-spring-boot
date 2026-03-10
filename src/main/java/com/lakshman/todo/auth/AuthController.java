package com.lakshman.todo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lakshman.todo.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google")
    public ResponseEntity<ApiResponse<AuthResponse>> googleCallBack(
            @Valid @RequestBody AuthRequest.GoogleRequest request, HttpServletResponse response) {
        log.info("The google auth request is like " + request);
        // get the auth code from google and then process furthur things
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.loginWithGoogle(request, response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerUserUsingEmailAndPassword(
            @Valid @RequestBody AuthRequest request, HttpServletResponse response) {
        log.info("The req body is" + request);
        // do the logging here
        // call the service
        // do the loggin
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.registerUserUsingEmailAndPassword(request, response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUserUsingEmailAndPassword(
            @Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        log.info("The login req body is" + request);
        // do the logging here
        // call the service
        // do the loggin
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.loginUserUsingEmailAndPassword(request, response));
    }
}