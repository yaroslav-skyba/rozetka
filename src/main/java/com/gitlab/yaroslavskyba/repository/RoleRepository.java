package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByUuid(UUID uuid);
    Optional<Role> findByNameRole(String nameRole);
    void deleteRoleByUuid(UUID uuid);
}