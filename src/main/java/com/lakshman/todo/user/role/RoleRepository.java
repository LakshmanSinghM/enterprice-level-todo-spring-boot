package com.lakshman.todo.user.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

    boolean existsById(Long id);

    boolean existsByName(String name);
}