package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByUuid(UUID uuid);
    Role findByNameRole(String nameRole);
}