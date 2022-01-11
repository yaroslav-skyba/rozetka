package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.model.User;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getByLogin(String login) {
        try {
            final User user = userRepository.findByLogin(login);

            return new UserDto(
                    user.getUuid(),
                    user.getRole().getUuid(),
                    user.getLogin(),
                    user.getPasswordUser(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBirthday()
            );
        } catch (Exception e) {
            throw new UserServiceException("An error occurred while getting a user by login", e);
        }
    }
}
