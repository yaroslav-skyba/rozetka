package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.model.User;
import com.gitlab.yaroslavskyba.repository.RoleRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto getByLogin(String login) {
        try {
            final User user = userRepository.findByLogin(login).orElseThrow(() -> new UserServiceException("A user was not found"));
            return new UserDto(user.getUuid(),
                               user.getRole().getUuid(),
                               user.getLogin(),
                               user.getPasswordUser(),
                               user.getEmail(),
                               user.getFirstName(),
                               user.getLastName(),
                               user.getBirthday());
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while getting a user by login", exception);
        }
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        try {
            final User user = new User();
            user.setUuid(UUID.randomUUID());
            user.setRole(roleRepository.findByUuid(userDto.getRoleUuid()));
            user.setLogin(userDto.getLogin());
            user.setPasswordUser(userDto.getPasswordUser());
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setBirthday(userDto.getBirthday());

            userRepository.saveAndFlush(user);

            return userDto;
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while creating a user", exception);
        }
    }
}
