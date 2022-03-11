package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.RoleDto;
import com.gitlab.yaroslavskyba.exception.RoleServiceException;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    void createRole(RoleDto roleDto) throws RoleServiceException;
    RoleDto getRoleByName(String nameRole) throws RoleServiceException;
    List<RoleDto> getRoleList() throws RoleServiceException;
    void updateRoleByUuid(UUID uuid, RoleDto roleDto) throws RoleServiceException;
    void deleteRoleByUuid(UUID uuid) throws RoleServiceException;
}
