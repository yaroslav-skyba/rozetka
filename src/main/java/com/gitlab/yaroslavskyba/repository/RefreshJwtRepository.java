package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.RefreshJwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshJwtRepository extends JpaRepository<RefreshJwt, Long> {
    Optional<RefreshJwt> findRefreshTokenByValue(String value);
}