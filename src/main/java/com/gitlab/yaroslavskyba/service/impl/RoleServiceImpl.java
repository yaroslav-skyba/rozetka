package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.RoleDto;
import com.gitlab.yaroslavskyba.exception.RoleServiceException;
import com.gitlab.yaroslavskyba.model.Role;
import com.gitlab.yaroslavskyba.repository.RoleRepository;
import com.gitlab.yaroslavskyba.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto getByUuid(UUID uuid) {
        try {
            return new RoleDto(uuid, roleRepository.findByUuid(uuid).getNameRole());
        } catch (Exception e) {
            throw new RoleServiceException("An error occurred while getting a role by uuid", e);
        }
    }

    @Override
    public RoleDto getByName(String nameRole) {
        try {
            return new RoleDto(roleRepository.findByNameRole(nameRole).getUuid(), nameRole);
        } catch (Exception e) {
            throw new RoleServiceException("An error occurred while getting a role by uuid", e);
        }
    }

    @Override
    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        try {
            final Role role = new Role();
            role.setUuid(UUID.randomUUID());
            role.setNameRole(roleDto.getNameRole());

            roleRepository.saveAndFlush(role);

            return new RoleDto(role.getUuid(), role.getNameRole());
        } catch (Exception e) {
            throw new RoleServiceException("An error occurred while creating a role", e);
        }
    }
}
