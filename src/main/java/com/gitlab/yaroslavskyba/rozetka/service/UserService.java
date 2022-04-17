package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.exception.UserServiceException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void create(UserDto userDto) throws UserServiceException;
    UserDto get(String login) throws UserServiceException;
    List<UserDto> getList() throws UserServiceException;
    void update(UserDto userDto) throws UserServiceException;
    void delete(UUID uuid) throws UserServiceException;
}
