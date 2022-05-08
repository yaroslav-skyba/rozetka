package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;

public interface JwtService {
    String create(UserDto userDto);
    String refreshJwtValue(String jwtValue);
    void delete(String jwtValue);
    String getUsernameByJwtValue(String jwtValue);
    boolean isJwtValueValid(String jwtValue);
}
