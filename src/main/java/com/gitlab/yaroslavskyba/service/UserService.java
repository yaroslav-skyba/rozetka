package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserDto userDto) throws UserServiceException;
    UserDto getUserByUuid(UUID uuid) throws UserServiceException;
    UserDto getUserByLogin(String login) throws UserServiceException;
    List<UserDto> getUserList() throws UserServiceException;
    void updateUserByUuid(UUID uuid, UserDto userDto) throws UserServiceException;
    void deleteUserByUuid(UUID uuid) throws UserServiceException;
}
