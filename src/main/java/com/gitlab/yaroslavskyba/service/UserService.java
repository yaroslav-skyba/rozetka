package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getUserList();
    UserDto getUserByLogin(String login);
}
