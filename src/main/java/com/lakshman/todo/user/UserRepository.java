package com.lakshman.todo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lakshman.todo.contants.enums.RoleType;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<UserEntity> findByEmail(String email);

    @Query("""
                SELECT DISTINCT u FROM UserEntity u
                LEFT JOIN FETCH u.roles r
                LEFT JOIN FETCH r.permissions
                LEFT JOIN FETCH u.directPermissions
                WHERE u.email = :email AND r.name= :role
            """)
    Optional<UserEntity> findUserWithAuthorities(String email, RoleType role);
}