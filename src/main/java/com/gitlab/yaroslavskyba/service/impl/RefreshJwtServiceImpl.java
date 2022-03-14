package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.exception.RefreshJwtServiceException;
import com.gitlab.yaroslavskyba.model.RefreshJwt;
import com.gitlab.yaroslavskyba.repository.RefreshJwtRepository;
import com.gitlab.yaroslavskyba.security.JwtTokenUtil;
import com.gitlab.yaroslavskyba.service.RefreshJwtService;
import com.gitlab.yaroslavskyba.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefreshJwtServiceImpl implements RefreshJwtService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RefreshJwtRepository refreshJwtRepository;

    public RefreshJwtServiceImpl(JwtTokenUtil jwtTokenUtil, UserService userService, RefreshJwtRepository refreshJwtRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.refreshJwtRepository = refreshJwtRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public String getRefreshJwtByValue(String value) {
        try {
            return jwtTokenUtil.generateAccessToken(userService.getUserByLogin(
                verifyExpiration(refreshJwtRepository.findRefreshTokenByValue(value).orElseThrow()).getUser().getLogin()));
        } catch (Exception exception) {
            throw new RefreshJwtServiceException("An error occurred while getting a refresh jwt", exception);
        }
    }

    @Override
    public RefreshJwt verifyExpiration(RefreshJwt refreshJwt) {

    }
}
