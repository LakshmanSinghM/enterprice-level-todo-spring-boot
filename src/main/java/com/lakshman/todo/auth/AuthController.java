package com.lakshman.todo.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.utils.ResponseBuilders;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/google/callback")
    public ResponseEntity<?> googleCallBack(@RequestParam("code") String code) {
        // get the auth code from google and then process furthur things
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerUserUsingEmailAndPassword(@RequestBody AuthRequest request) {
            // do the logging here 
            //call the service 
            // do the loggin  
     return ResponseEntity.ok(null) ;  
    }
}