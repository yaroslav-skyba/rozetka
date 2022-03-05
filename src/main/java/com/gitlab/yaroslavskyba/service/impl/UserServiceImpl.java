package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.exception.UserServiceException;
import com.gitlab.yaroslavskyba.model.User;
import com.gitlab.yaroslavskyba.repository.RoleRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_SERVICE_EXCEPTION_MESSAGE = "A user was not found";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        try {
            final User user = new User();
            user.setUuid(UUID.randomUUID());
            user.setRole(roleRepository.findByUuid(userDto.getRoleUuid()));
            user.setLogin(userDto.getLogin());
            user.setPasswordUser(passwordEncoder.encode(userDto.getPasswordUser()));
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setBirthday(userDto.getBirthday());

            userRepository.saveAndFlush(user);
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while creating a user", exception);
        }
    }

    @Override
    public List<UserDto> getUserList() {
        try {
            final List<UserDto> userDtoList = new ArrayList<>();

            for (User user : userRepository.findAll()) {
                userDtoList.add(getUserByLogin(user.getLogin()));
            }

            return userDtoList;
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a user list", exception);
        }
    }

    @Override
    public UserDto getUserByLogin(String login) {
        try {
            final User user = userRepository.findByLogin(login).orElseThrow(() -> new UserServiceException(USER_SERVICE_EXCEPTION_MESSAGE));
            return new UserDto(user.getUuid(), user.getRole().getUuid(), user.getLogin(), user.getPasswordUser(), user.getEmail(),
                               user.getFirstName(), user.getLastName(), user.getBirthday());
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while getting a user by a login", exception);
        }
    }

    @Override
    @Transactional
    public void updateByUuid(UUID uuid, UserDto userDto) {
        try {
            final User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserServiceException(USER_SERVICE_EXCEPTION_MESSAGE));

            user.setUuid(userDto.getUuid());
            user.setRole(roleRepository.findByUuid(userDto.getRoleUuid()));
            user.setLogin(userDto.getLogin());

            final String password = userDto.getPasswordUser();

            if (password != null) {
                user.setPasswordUser(passwordEncoder.encode(password));
            }

            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setBirthday(userDto.getBirthday());

            userRepository.saveAndFlush(user);
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while updating a user", exception);
        }
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        try {
            userRepository.deleteById(
                userRepository.findByUuid(uuid).orElseThrow(() -> new UserServiceException(USER_SERVICE_EXCEPTION_MESSAGE)).getIdUser());
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while deleting a user", exception);
        }
    }
}
