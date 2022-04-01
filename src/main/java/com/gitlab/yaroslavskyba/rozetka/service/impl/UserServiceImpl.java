package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.exception.UserServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.User;
import com.gitlab.yaroslavskyba.rozetka.repository.RoleRepository;
import com.gitlab.yaroslavskyba.rozetka.repository.UserRepository;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import com.gitlab.yaroslavskyba.rozetka.util.RoleName;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDto userDto) {
        try {
            final User user = new User();
            user.setUuid(UUID.randomUUID());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            userRepository.saveAndFlush(setUserFields(userDto, user));
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while creating a user", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByLogin(String login) {
        try {
            final User user = userRepository.findUserByLogin(login).orElseThrow();
            return new UserDto(user.getUuid(), user.getRole().getUuid(), user.getLogin(), user.getPassword(), user.getEmail(),
                               user.getFirstName(), user.getLastName(), user.getBirthday());
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while getting a user", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUserList() {
        try {
            final List<UserDto> userDtoList =
                userRepository.findAll().stream().map(user -> getUserByLogin(user.getLogin())).collect(Collectors.toList());

            if (userDtoList.isEmpty()) {
                throw new UserServiceException("A user list is empty");
            }

            return userDtoList;
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while getting a user list", exception);
        }
    }

    @Override
    public void updateUserByUuid(UserDto userDto) {
        try {
            final User user = userRepository.findUserByUuid(userDto.getUuid()).orElseThrow();
            final String password = userDto.getPassword();

            if (password != null) {
                user.setPassword(passwordEncoder.encode(password));
            }

            userRepository.saveAndFlush(setUserFields(userDto, user));
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while updating a user", exception);
        }
    }

    @Override
    public void deleteUserByUuid(UUID uuid) {
        try {
            userRepository.delete(userRepository.findUserByUuid(uuid).orElseThrow());
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while deleting a user", exception);
        }
    }

    private User setUserFields(UserDto userDto, User user) {
        final UUID uuidRole = userDto.getUuidRole();

        if (uuidRole != null) {
            user.setRole(roleRepository.findRoleByUuid(uuidRole).orElseThrow());
        } else {
            user.setRole(roleRepository.findRoleByName(RoleName.USER).orElseThrow());
        }

        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());

        return user;
    }
}