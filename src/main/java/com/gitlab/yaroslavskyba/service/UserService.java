package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getUserList();
    UserDto getUserByLogin(String login);
    void deleteByUuid(UUID uuid);
}
