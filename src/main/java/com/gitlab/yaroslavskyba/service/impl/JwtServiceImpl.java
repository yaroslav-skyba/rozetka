package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.Main;
import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.JwtServiceException;
import com.gitlab.yaroslavskyba.model.Jwt;
import com.gitlab.yaroslavskyba.repository.JwtRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.service.JwtService;
import com.gitlab.yaroslavskyba.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

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
    public String createJwt(UserDto userDto) {
        try {
            final String jwtValue = generateJwtValue(userDto);
            final Jwt jwt = new Jwt();
            jwt.setUuid(UUID.randomUUID());
            jwt.setUser(userRepository.findUserByUuid(userDto.getUuid()).orElseThrow());
            jwt.setValue(TOKEN_TYPE + jwtValue);
            jwt.setExpiryDate(Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtValue).getBody().getExpiration().toInstant());

            jwtRepository.saveAndFlush(jwt);

            return jwtValue;
        } catch (Exception exception) {
            throw new JwtServiceException("An error occurred while creating a jwt", exception);
        }
    }

    @Override
    @Transactional
    public String refreshJwtValue(String value) {
        try {
            final Jwt jwt = jwtRepository.findJwtByValue(value).orElseThrow();

            if (jwt.getExpiryDate().compareTo(Instant.now()) < 0) {
                jwtRepository.delete(jwt);
                throw new JwtServiceException("A jwt has been expired. Please, make a new sign-in request");
            }

            final String jwtValue = TOKEN_TYPE + generateJwtValue(userService.getUserByLogin(jwt.getUser().getLogin()));
            jwt.setValue(jwtValue);
            jwtRepository.saveAndFlush(jwt);

            return jwtValue;
        } catch (Exception exception) {
            throw new JwtServiceException("An error occurred while refreshing a jwt value", exception);
        }
    }

    @Override
    public String getUsernameByJwtValue(String jwtValue) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtValue).getBody().getSubject();
    }

    @Override
    public boolean isJwtValueValid(String jwtValue) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtValue);
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
