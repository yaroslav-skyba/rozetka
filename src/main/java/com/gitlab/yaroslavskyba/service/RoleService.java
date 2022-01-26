package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.RoleDto;

import java.util.UUID;

public interface RoleService {
    RoleDto getByUuid(UUID uuid);
    RoleDto getByName(String nameRole);
    RoleDto createRole(RoleDto roleDto);
}
