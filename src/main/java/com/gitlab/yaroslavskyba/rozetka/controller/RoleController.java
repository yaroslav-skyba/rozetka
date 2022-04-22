package com.gitlab.yaroslavskyba.rozetka.controller;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.exception.RoleServiceException;
import com.gitlab.yaroslavskyba.rozetka.service.RoleService;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import com.gitlab.yaroslavskyba.rozetka.util.ControllerPath;
import com.gitlab.yaroslavskyba.rozetka.util.MediaType;
import com.gitlab.yaroslavskyba.rozetka.util.RoleName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping(value = ControllerPath.ROLES, consumes = MediaType.ROLE, produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createRole(@RequestBody RoleDto roleDto) {
        try {
            roleService.create(roleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("A role has been successfully created");
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(roleServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.ROLE, produces = MediaType.ROLE)
    public ResponseEntity<RoleDto> getRole(@PathVariable UUID uuid, Principal principal) {
        try {
            final UUID userUuid = userService.get(principal.getName()).getUuidRole();

            if (RoleName.USER.equals(roleService.get(userUuid).getName())) {
                if (uuid.equals(userUuid)) {
                    return ResponseEntity.ok(roleService.get(RoleName.USER));
                }

                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(roleService.get(uuid));
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = ControllerPath.ROLES, produces = MediaType.ROLE_LIST)
    public ResponseEntity<List<RoleDto>> getRoleList() {
        try {
            return ResponseEntity.ok(roleService.getList());
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = ControllerPath.ROLES, consumes = MediaType.ROLE)
    public ResponseEntity<String> updateRole(@RequestBody RoleDto roleDto) {
        try {
            roleService.update(roleDto);
            return ResponseEntity.ok("A role has been successfully updated");
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(roleServiceException.getMessage());
        }
    }

    @DeleteMapping(ControllerPath.ROLE)
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid) {
        try {
            roleService.delete(uuid);
            return ResponseEntity.noContent().build();
        } catch (RoleServiceException roleServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(roleServiceException.getMessage());
        }
    }
}
