package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.model.User;
import com.gitlab.yaroslavskyba.repository.RoleRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.service.UserService;
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
            user.setPasswordUser(passwordEncoder.encode(userDto.getPasswordUser()));
            setUserFields(userDto, user);

            userRepository.saveAndFlush(user);
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while creating a user", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByLogin(String login) {
        try {
            final User user = userRepository.findUserByLogin(login).orElseThrow();
            return new UserDto(user.getUuid(), user.getRole().getUuid(), user.getLogin(), user.getPasswordUser(), user.getEmail(),
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
    public void updateUserByUuid(UUID uuid, UserDto userDto) {
        try {
            final User user = userRepository.findUserByUuid(uuid).orElseThrow();
            user.setUuid(userDto.getUuid());
            setUserFields(userDto, user);

            final String password = userDto.getPasswordUser();

            if (password != null) {
                user.setPasswordUser(passwordEncoder.encode(password));
            }

            userRepository.saveAndFlush(user);
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

    private void setUserFields(UserDto userDto, User user) {
        user.setRole(roleRepository.findRoleByUuid(userDto.getUuidRole()).orElseThrow());
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());
    }
}
