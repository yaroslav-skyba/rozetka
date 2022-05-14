package com.gitlab.yaroslavskyba.rozetka.controller;

import com.gitlab.yaroslavskyba.rozetka.dto.LoginDto;
import com.gitlab.yaroslavskyba.rozetka.exception.JwtServiceException;
import com.gitlab.yaroslavskyba.rozetka.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.rozetka.service.JwtService;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import com.gitlab.yaroslavskyba.rozetka.util.ControllerPath;
import com.gitlab.yaroslavskyba.rozetka.util.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ControllerPath.JWTS, produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
public class JwtController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public JwtController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.LOGIN)
    public ResponseEntity<String> createJwt(@RequestBody LoginDto loginDto) {
        try {
            final String username = loginDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));

            return ResponseEntity.status(HttpStatus.CREATED).body(jwtService.create(userService.get(username)));
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(runtimeException.getMessage());
        }
    }

    @PutMapping(consumes = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updateJwt(@RequestBody String value) {
        try {
            return ResponseEntity.ok(jwtService.update(value));
        } catch (JwtServiceException jwtServiceException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtServiceException.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteJwt(@RequestBody String value) {
        try {
            jwtService.delete(value);
            return ResponseEntity.noContent().build();
        } catch (ReviewServiceException reviewServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(reviewServiceException.getMessage());
        }
    }
}
