package com.lakshman.todo.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lakshman.todo.auth.AuthRequest;
import com.lakshman.todo.auth.AuthResponse;
import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.user.dto.UserProfileResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {
        log.info("The user profile request is like ");
        // get the auth code from google and then process furthur things
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserProfile());
    }
}