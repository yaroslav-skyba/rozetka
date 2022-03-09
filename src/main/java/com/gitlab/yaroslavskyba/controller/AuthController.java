package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.LoginRequest;
import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.security.JwtTokenUtil;
import com.gitlab.yaroslavskyba.service.RoleService;
import com.gitlab.yaroslavskyba.service.UserService;
import com.gitlab.yaroslavskyba.util.ControllerPath;
import com.gitlab.yaroslavskyba.util.MediaType;
import com.gitlab.yaroslavskyba.util.RoleName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtTokenUtil jwtTokenUtil, UserService userService, RoleService roleService,
                          AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = ControllerPath.LOGINS, consumes = MediaType.LOGIN_REQUEST)
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            final String username = loginRequest.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));

            return ResponseEntity.ok(jwtTokenUtil.generateAccessToken(userService.getUserByLogin(username)));
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("An incorrect login or password");
        }
    }

    @PostMapping(value = ControllerPath.REGISTRATIONS, consumes = MediaType.USER)
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userDto.setRoleUuid(roleService.getRoleByName(RoleName.USER).getUuid());
            userService.createUser(userDto);

            return ResponseEntity.status(HttpStatus.CREATED).body("You were successfully registered");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }
}
