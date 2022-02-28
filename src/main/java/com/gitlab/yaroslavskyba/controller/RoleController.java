package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.util.MediaType;
import com.gitlab.yaroslavskyba.dto.RoleDto;
import com.gitlab.yaroslavskyba.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = "roles", consumes = MediaType.ROLE)
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDto));
    }

    @GetMapping(value = "roles", produces = MediaType.ROLE_LIST)
    public ResponseEntity<List<RoleDto>> getProductList() {
        return ResponseEntity.ok(roleService.getRoleList());
    }
}
