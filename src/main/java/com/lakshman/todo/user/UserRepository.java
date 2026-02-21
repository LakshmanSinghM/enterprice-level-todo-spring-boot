package com.lakshman.todo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<UserEntity> findByEmail(String email);
}