package com.lakshman.todo.security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lakshman.todo.contants.enums.RoleType;
import com.lakshman.todo.user.UserEntity;
import com.lakshman.todo.user.UserRepository;
import com.lakshman.todo.user.role.RoleEntity;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Handle null roles just in case, though logically shouldn't happen if
        // initialized
        if (user.getRoles() != null) {
            for (RoleEntity role : user.getRoles()) {
                // Add Role Authority
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));

                // Add Permission Authorities
                // if (role.getPermissions() != null) {
                //     for (PermissionEntity permission : role.getPermissions()) {
                //         authorities.add(new SimpleGrantedAuthority(permission.getName()));
                //     }
                // }
            }
        }

        // If no roles/authorities, maybe assign default or leave empty (Spring Security
        // handles empty authorities fine usually, or we can enforce)
        if (authorities.isEmpty()) {
            // Optional: Add default role if needed, or just proceed.
            // Logic in original code added default USER role if empty. preserving behavior
            // optionally?
            // Original: "Assign default role USER if no roles are present"
            // Implementation:
            authorities.add(new SimpleGrantedAuthority("ROLE_" + RoleType.USER.name()));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}