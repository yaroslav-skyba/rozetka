package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.exception.RoleServiceException;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    void createRole(RoleDto roleDto) throws RoleServiceException;
    RoleDto getRoleByName(String nameRole) throws RoleServiceException;
    List<RoleDto> getRoleList() throws RoleServiceException;
    void updateRoleByUuid(RoleDto roleDto) throws RoleServiceException;
    void deleteRoleByUuid(UUID uuid) throws RoleServiceException;
}
