package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.RoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    List<RoleDto> getRoleList();
    RoleDto getRoleByUuid(UUID uuid);
    RoleDto getRoleByName(String nameRole);
}
