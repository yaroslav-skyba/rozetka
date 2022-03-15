package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.dto.LoginDto;
import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.JwtServiceException;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.service.JwtService;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, RoleService roleService,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping(value = ControllerPath.LOGINS, consumes = MediaType.LOGIN_REQUEST)
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            final String username = loginDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));

            return ResponseEntity.ok(jwtService.createJwt(userService.getUserByLogin(username)));
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("An incorrect login or password");
        }
    }

    @PostMapping(value = ControllerPath.REGISTRATIONS, consumes = MediaType.USER)
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userDto.setUuidRole(roleService.getRoleByName(RoleName.USER).getUuid());
            userService.createUser(userDto);

            return ResponseEntity.status(HttpStatus.CREATED).body("You were successfully registered");
        } catch (UserServiceException userServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userServiceException.getMessage());
        }
    }

    @PostMapping(value = ControllerPath.REFRESH_JWT, consumes = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> refreshJwtValue(@RequestBody String jwtValue) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(jwtService.refreshJwtValue(jwtValue));
        } catch (JwtServiceException jwtServiceException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtServiceException.getMessage());
        }
    }
}
