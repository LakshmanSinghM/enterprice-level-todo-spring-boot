package com.lakshman.todo.user.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lakshman.todo.contants.enums.RoleType;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(RoleType name);

    boolean existsById(Long id);

    boolean existsByName(String name);
}