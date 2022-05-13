package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.exception.RoleServiceException;

import com.gitlab.yaroslavskyba.rozetka.model.Role;
import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role create(RoleDto roleDto) throws RoleServiceException;
    RoleDto get(UUID uuid) throws RoleServiceException;
    RoleDto get(String name) throws RoleServiceException;
    List<RoleDto> getList() throws RoleServiceException;
    void update(RoleDto roleDto) throws RoleServiceException;
    void delete(UUID uuid) throws RoleServiceException;
}
