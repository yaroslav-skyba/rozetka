package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.exception.UserServiceException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserDto userDto) throws UserServiceException;
    UserDto getUserByLogin(String login) throws UserServiceException;
    List<UserDto> getUserList() throws UserServiceException;
    void updateUserByUuid(UUID uuid, UserDto userDto) throws UserServiceException;
    void deleteUserByUuid(UUID uuid) throws UserServiceException;
}
