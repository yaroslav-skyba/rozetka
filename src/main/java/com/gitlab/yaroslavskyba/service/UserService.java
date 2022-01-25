package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.UserDto;

public interface UserService {
    UserDto getByLogin(String login);
    UserDto create(UserDto userDto);
}
