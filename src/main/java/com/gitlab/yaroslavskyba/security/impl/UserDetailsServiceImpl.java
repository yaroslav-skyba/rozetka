package com.gitlab.yaroslavskyba.security.impl;

import com.gitlab.yaroslavskyba.repository.OrderItemRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findUserByLogin(username).orElseThrow());
    }
}

