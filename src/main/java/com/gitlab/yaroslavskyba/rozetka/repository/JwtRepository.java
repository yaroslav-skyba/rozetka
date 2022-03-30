package com.gitlab.yaroslavskyba.rozetka.repository;

import com.gitlab.yaroslavskyba.rozetka.model.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Optional<Jwt> findJwtByValue(String value);
}