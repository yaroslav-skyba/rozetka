package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.Main;
import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.exception.JwtServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.Jwt;
import com.gitlab.yaroslavskyba.rozetka.repository.JwtRepository;
import com.gitlab.yaroslavskyba.rozetka.repository.UserRepository;
import com.gitlab.yaroslavskyba.rozetka.service.JwtService;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String TOKEN_TYPE = "Bearer ";
    private static final String JWT_SECRET = "qYrYaF92NupxVj-LnfqTXmdfoi19Zf-KpPIkUw-YzEWDRK32uRKpwHbH139__8XK";

    private final UserRepository userRepository;
    private final JwtRepository jwtRepository;
    private final UserService userService;

    public JwtServiceImpl(UserRepository userRepository, JwtRepository jwtRepository, UserService userService) {
        this.userRepository = userRepository;
        this.jwtRepository = jwtRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public String create(UserDto userDto) {
        try {
            final String jwtValue = generateJwtValue(userDto);
            final String fullJwtValue = TOKEN_TYPE + jwtValue;

            final Jwt jwt = new Jwt();
            jwt.setUser(userRepository.findUserByUuid(userDto.getUuid()).orElseThrow());
            jwt.setValue(fullJwtValue);

            jwtRepository.saveAndFlush(jwt);

            return fullJwtValue;
        } catch (Exception exception) {
            throw new JwtServiceException("An error occurred while creating a jwt", exception);
        }
    }

    @Override
    @Transactional
    public String update(String value) {
        try {
            final Jwt jwt = jwtRepository.findJwtByValue(value).orElseThrow();
            final String jwtValue = TOKEN_TYPE + generateJwtValue(userService.get(jwt.getUser().getLogin()));

            jwt.setValue(jwtValue);
            jwtRepository.saveAndFlush(jwt);

            return jwtValue;
        } catch (Exception exception) {
            throw new JwtServiceException("An error occurred while refreshing a jwt value", exception);
        }
    }

    @Override
    @Transactional
    public void delete(String jwtValue) {
        try {
            jwtRepository.delete(jwtRepository.findJwtByValue(jwtValue).orElseThrow());
        } catch (Exception exception) {
            throw new JwtServiceException("An error occurred while deleting a JWT", exception);
        }
    }

    @Override
    public String getUsernameByJwtValue(String value) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(value).getBody().getSubject();
    }

    @Override
    public boolean isJwtValueValid(String value) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(value);
            return true;
        } catch (SignatureException signatureException) {
            return false;
        }
    }

    private String generateJwtValue(UserDto userDto) {
        return Jwts.builder().setSubject(userDto.getLogin()).setIssuer(Main.class.getPackageName()).setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 10_000_000_000L)).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }
}
