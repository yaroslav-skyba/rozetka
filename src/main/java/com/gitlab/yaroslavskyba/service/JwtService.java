package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.model.Jwt;

public interface JwtService {
    void createJwt(UserDto userDto);
    String generateJwtValue(UserDto userDto);
    String refreshJwtValue(String jwtValue);
    String getUsernameByJwtValue(String jwtValue);
    boolean isJwtValueValid(String jwtValue);
}
