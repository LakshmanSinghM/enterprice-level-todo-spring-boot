package com.lakshman.todo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.utils.ResponseBuilders;

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

    @GetMapping("/google/callback")
    public ResponseEntity<?> googleCallBack(@RequestParam("code") String code) {
        // get the auth code from google and then process furthur things
        return null;
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
             @Valid  @RequestBody LoginRequest request, HttpServletResponse response) {
        log.info("The login req body is" + request);
        // do the logging here
        // call the service
        // do the loggin
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.loginUserUsingEmailAndPassword(request, response));
    }
}