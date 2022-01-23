package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.AuthRequest;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.security.JwtTokenUtil;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<AuthRequest> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(userRepository.findByLogin(((UserDetails)authenticate.getPrincipal()).getUsername()).orElseThrow())).body(request);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*@PostMapping("register")
    public UserView register(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }*/

}
