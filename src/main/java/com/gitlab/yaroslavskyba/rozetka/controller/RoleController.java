package com.gitlab.yaroslavskyba.rozetka.controller;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.exception.RoleServiceException;
import com.gitlab.yaroslavskyba.rozetka.service.RoleService;
import com.gitlab.yaroslavskyba.rozetka.util.ControllerPath;
import com.gitlab.yaroslavskyba.rozetka.util.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = ControllerPath.ROLES, consumes = MediaType.ROLE, produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createRole(@RequestBody RoleDto roleDto) {
        try {
            roleService.createRole(roleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("A role has been successfully created");
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(roleServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.ROLES, produces = MediaType.ROLE_LIST)
    public ResponseEntity<List<RoleDto>> getRoleList() {
        try {
            return ResponseEntity.ok(roleService.getRoleList());
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = ControllerPath.ROLE, consumes = MediaType.ROLE)
    public ResponseEntity<String> updateRole(@PathVariable UUID uuid, @RequestBody RoleDto roleDto) {
        try {
            roleService.updateRoleByUuid(uuid, roleDto);
            return ResponseEntity.ok("A role has been successfully updated");
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(roleServiceException.getMessage());
        }
    }

    @DeleteMapping(ControllerPath.ROLE)
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid) {
        try {
            roleService.deleteRoleByUuid(uuid);
            return ResponseEntity.ok("A role has been successfully deleted");
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(roleServiceException.getMessage());
        }
    }
}
