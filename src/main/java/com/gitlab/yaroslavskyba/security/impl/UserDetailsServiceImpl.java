package com.gitlab.yaroslavskyba.security.impl;

import com.gitlab.yaroslavskyba.dto.UserDto;
import com.gitlab.yaroslavskyba.service.RoleService;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.util.Collections.singleton;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserDto userDto = userService.getByLogin(username);

        if (userDto == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(
                username,
                userDto.getPasswordUser(),
                singleton(new SimpleGrantedAuthority(roleService.getByUuid(userDto.getUuid()).getNameRole()))
        );
    }
}
