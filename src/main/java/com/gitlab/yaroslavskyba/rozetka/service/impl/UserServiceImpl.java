package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.RoleDto;
import com.gitlab.yaroslavskyba.rozetka.dto.UserDto;
import com.gitlab.yaroslavskyba.rozetka.exception.UserServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.User;
import com.gitlab.yaroslavskyba.rozetka.repository.RoleRepository;
import com.gitlab.yaroslavskyba.rozetka.repository.UserRepository;
import com.gitlab.yaroslavskyba.rozetka.service.RoleService;
import com.gitlab.yaroslavskyba.rozetka.service.UserService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.gitlab.yaroslavskyba.rozetka.util.RoleName.USER;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(UserDto userDto) {
        try {
            final User user = new User();
            user.setUuid(UUID.randomUUID());
            setFields(userDto, user);

            final String password = userDto.getPassword();
            setPassword(password == null ? "" : password, user);

            userRepository.saveAndFlush(user);
        } catch (ConstraintViolationException constraintViolationException) {
            throw new UserServiceException(constraintViolationException.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while creating a user", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto get(String login) {
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
    public List<UserDto> getList() {
        try {
            final List<UserDto> userDtoList =
                userRepository.findAll().stream().map(user -> get(user.getLogin())).collect(Collectors.toList());

            if (userDtoList.isEmpty()) {
                throw new UserServiceException("A user list is empty");
            }

            return userDtoList;
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while getting a user list", exception);
        }
    }

    @Override
    public void update(UserDto userDto) {
        try {
            final User user = userRepository.findUserByUuid(userDto.getUuid()).orElseThrow();
            setFields(userDto, user);

            final String password = userDto.getPassword();
            if (password != null) {
                setPassword(password, user);
            }

            userRepository.saveAndFlush(user);
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while updating a user", exception);
        }
    }

    @Override
    public void delete(UUID uuid) {
        try {
            userRepository.delete(userRepository.findUserByUuid(uuid).orElseThrow());
        } catch (Exception exception) {
            throw new UserServiceException("An error occurred while deleting a user", exception);
        }
    }

    private void setFields(UserDto userDto, User user) {
        user.setRole(roleRepository.findRoleByUuid(userDto.getUuidRole()).orElse(roleRepository.findRoleByName(USER).orElseGet(() -> {
            final RoleDto roleDto = new RoleDto();
            roleDto.setName(USER);

            return roleService.create(roleDto);
        })));
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());
    }

    private void setPassword(String password, User user) {
        if (password.isBlank()) {
            user.setPassword(password);
        } else {
            user.setPassword(passwordEncoder.encode(password));
        }
    }
}
