package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.AuthRequest;
import com.gitlab.yaroslavskyba.MediaType;
import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.security.JwtTokenUtil;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthController(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping(value = "logins", consumes = MediaType.AUTH_REQUEST)
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(jwtTokenUtil.generateAccessToken(userService.getByLogin(authRequest.getUsername())));
        } catch (AuthenticationException authenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("An incorrect login or password");
        }
    }

    @PostMapping(value = "registers", consumes = MediaType.USER)
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);
        }
    }
}
