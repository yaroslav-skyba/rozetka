package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.AuthRequest;
import com.gitlab.yaroslavskyba.MediaType;
import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.security.JwtTokenUtil;
import com.gitlab.yaroslavskyba.service.RoleService;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtTokenUtil jwtTokenUtil,
                          UserService userService,
                          RoleService roleService,
                          AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "logins", consumes = MediaType.AUTH_REQUEST)
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(
                jwtTokenUtil.generateAccessToken(
                    userService.getByLogin(
                        ((UserDetails)authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                authRequest.getUsername(), authRequest.getPassword())).getPrincipal()).getUsername())));

        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("An incorrect login or password");
        }
    }

    @PostMapping(value = "registrations", consumes = MediaType.USER)
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userDto.setUuid(UUID.randomUUID());
            userDto.setRoleUuid(roleService.getByName("user").getUuid());

            return ResponseEntity.status(HttpStatus.CREATED).body("You were successfully registered");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }
}
