package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Optional<Jwt> findJwtByValue(String value);
}