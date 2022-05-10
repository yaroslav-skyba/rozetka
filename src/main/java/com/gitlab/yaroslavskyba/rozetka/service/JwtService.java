package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;

public interface JwtService {
    String create(UserDto userDto);
    String update(String jwtValue);
    void delete(String jwtValue);

    String getUsernameByJwtValue(String value);
    boolean isJwtValueValid(String value);
}
