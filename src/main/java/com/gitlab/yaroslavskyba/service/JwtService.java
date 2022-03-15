package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.UserDto;

public interface JwtService {
    String createJwt(UserDto userDto);
    String refreshJwtValue(String jwtValue);
    String getUsernameByJwtValue(String jwtValue);
    boolean isJwtValueValid(String jwtValue);
}
