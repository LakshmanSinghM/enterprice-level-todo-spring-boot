package com.lakshman.todo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class AuthRequest {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank 
    private String password;
    // private String code; // Google authorization code
    // private String provider; // GOOGLE, LOCAL, TWITTER
}