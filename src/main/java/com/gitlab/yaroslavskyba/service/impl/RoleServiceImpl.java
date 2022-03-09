package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.RoleDto;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.exception.RoleServiceException;
import com.gitlab.yaroslavskyba.model.Role;
import com.gitlab.yaroslavskyba.repository.RoleRepository;
import com.gitlab.yaroslavskyba.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void createRole(RoleDto roleDto) {
        try {
            final Role role = new Role();
            role.setUuid(UUID.randomUUID());
            role.setName(roleDto.getName());

            roleRepository.saveAndFlush(role);
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while creating a role", exception);
        }
    }

    @Override
    public RoleDto getRoleByUuid(UUID uuid) {
        try {
            final Role role = roleRepository.findRoleByUuid(uuid).orElseThrow();
            return new RoleDto(role.getUuid(), role.getName());
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while getting a role", exception);
        }
    }

    @Override
    public RoleDto getRoleByName(String nameRole) {
        try {
            final Role role = roleRepository.findRoleByName(nameRole).orElseThrow();
            return new RoleDto(role.getUuid(), role.getName());
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while getting a role", exception);
        }
    }

    @Override
    public List<RoleDto> getRoleList() {
        try {
            final List<RoleDto> roleDtoList = new ArrayList<>();
            roleRepository.findAll().forEach(role -> roleDtoList.add(getRoleByUuid(role.getUuid())));

            return roleDtoList;
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a role list", exception);
        }
    }

    @Override
    public void updateRoleByUuid(UUID uuid, RoleDto roleDto) {
        try {
            final Role role = roleRepository.findRoleByUuid(uuid).orElseThrow();
            role.setUuid(roleDto.getUuid());
            role.setName(roleDto.getName());

            roleRepository.saveAndFlush(role);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while updating a role", exception);
        }
    }

    @Override
    public void deleteRoleByUuid(UUID uuid) {
        try {
            roleRepository.deleteRoleByUuid(uuid);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while deleting a role", exception);
        }
    }
}
