package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.util.ControllerPath;
import com.gitlab.yaroslavskyba.util.MediaType;
import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@RequestMapping(value = ControllerPath.USERS, produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.USER)
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("A user has been successfully created");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }

    @GetMapping(produces = MediaType.USER_LIST)
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PutMapping(value = ControllerPath.UUID, consumes = MediaType.USER)
    @PreAuthorize("#uuid.equals(principal.uuid)")
    public ResponseEntity<String> updateUser(@PathVariable UUID uuid, @RequestBody UserDto userDto) {
        try {
            userService.updateByUuid(uuid, userDto);
            return ResponseEntity.ok("A user has been successfully edited");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }

    @DeleteMapping(ControllerPath.UUID)
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid) {
        try {
            userService.deleteByUuid(uuid);
            return ResponseEntity.ok("A user has been successfully deleted");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }
}