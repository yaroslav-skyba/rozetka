package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.util.MediaType;
import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "users", consumes = MediaType.USER)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (UserServiceException userServiceException) {
            userDto.setLogin(userServiceException.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);
        }
    }

    @GetMapping(value = "users", produces = MediaType.USER_LIST)
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PutMapping(value = "users/{uuid}", consumes = MediaType.USER)
    public ResponseEntity<UserDto> deleteUser(@PathVariable UUID uuid, @RequestBody UserDto userDto) {
        try {
            userService.updateByUuid(uuid, userDto);
            return ResponseEntity.ok(userDto);
        } catch (UserServiceException userServiceException) {
            userDto.setLogin(userServiceException.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);
        }
    }

    @DeleteMapping(value = "users/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid) {
        try {
            userService.deleteByUuid(uuid);
            return ResponseEntity.noContent().build();
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }
}