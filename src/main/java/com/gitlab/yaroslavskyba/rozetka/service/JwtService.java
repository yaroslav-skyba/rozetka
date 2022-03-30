package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;

public interface JwtService {
    String createJwt(UserDto userDto);
    String refreshJwtValue(String jwtValue);
    String getUsernameByJwtValue(String jwtValue);
    boolean isJwtValueValid(String jwtValue);
}
