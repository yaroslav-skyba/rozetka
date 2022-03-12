package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.RoleDto;
import com.gitlab.yaroslavskyba.exception.RoleServiceException;
import com.gitlab.yaroslavskyba.model.Role;
import com.gitlab.yaroslavskyba.repository.RoleRepository;
import com.gitlab.yaroslavskyba.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public RoleDto getRoleByName(String nameRole) {
        try {
            final Role role = roleRepository.findRoleByName(nameRole).orElseThrow();
            return new RoleDto(role.getUuid(), role.getName());
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while getting a role", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRoleList() {
        try {
            final List<RoleDto> roleDtoList =
                roleRepository.findAll().stream().map(role -> new RoleDto(role.getUuid(), role.getName())).collect(Collectors.toList());

            if (roleDtoList.isEmpty()) {
                throw new RoleServiceException("A role list is empty");
            }

            return roleDtoList;
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while getting a role list", exception);
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
            throw new RoleServiceException("An error occurred while updating a role", exception);
        }
    }

    @Override
    public void deleteRoleByUuid(UUID uuid) {
        try {
            roleRepository.delete(roleRepository.findRoleByUuid(uuid).orElseThrow());
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while deleting a role", exception);
        }
    }
}
