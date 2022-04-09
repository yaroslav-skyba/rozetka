package com.gitlab.yaroslavskyba.rozetka.controller;

import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.exception.UserServiceException;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import com.gitlab.yaroslavskyba.rozetka.util.ControllerPath;
import com.gitlab.yaroslavskyba.rozetka.util.MediaType;
import com.gitlab.yaroslavskyba.rozetka.util.RoleName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = ControllerPath.USERS, consumes = MediaType.USER)
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("A user has been successfully created");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.USERS, produces = MediaType.USER_LIST)
    public ResponseEntity<List<UserDto>> getUserList() {
        try {
            return ResponseEntity.ok(userService.getUserList());
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    
    @PutMapping(value = ControllerPath.USERS, consumes = MediaType.USER)
    @PreAuthorize("#userDto.uuid.equals(principal.uuid) or hasAuthority('" + RoleName.ADMIN + "')")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        try {
            userService.updateUserByUuid(userDto);
            return ResponseEntity.ok("A user has been successfully updated");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }

    @DeleteMapping(ControllerPath.USER)
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid) {
        try {
            userService.deleteUserByUuid(uuid);
            return ResponseEntity.ok("A user has been successfully deleted");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }
}
