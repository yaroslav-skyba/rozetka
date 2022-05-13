package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.exception.RoleServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.Role;
import com.gitlab.yaroslavskyba.rozetka.repository.RoleRepository;
import com.gitlab.yaroslavskyba.rozetka.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role create(RoleDto roleDto) {
        try {
            final Role role = new Role();
            role.setUuid(UUID.randomUUID());
            role.setName(roleDto.getName());

            return roleRepository.saveAndFlush(role);
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while creating a role", exception);
        }
    }

    @Override
    public RoleDto get(UUID uuid) throws RoleServiceException {
        try {
            final Role role = roleRepository.findRoleByUuid(uuid).orElseThrow();
            return new RoleDto(role.getUuid(), role.getName());
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while getting a role", exception);
        }
    }

    @Override
    public RoleDto get(String name) {
        try {
            final Role role = roleRepository.findRoleByName(name).orElseThrow();
            return new RoleDto(role.getUuid(), role.getName());
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while getting a role", exception);
        }
    }

    @Override
    public List<RoleDto> getList() {
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
    @Transactional
    public void update(RoleDto roleDto) {
        try {
            final Role role = roleRepository.findRoleByUuid(roleDto.getUuid()).orElseThrow();
            role.setName(roleDto.getName());

            roleRepository.saveAndFlush(role);
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while updating a role", exception);
        }
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        try {
            roleRepository.delete(roleRepository.findRoleByUuid(uuid).orElseThrow());
        } catch (Exception exception) {
            throw new RoleServiceException("An error occurred while deleting a role", exception);
        }
    }
}
