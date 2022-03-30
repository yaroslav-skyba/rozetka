package com.gitlab.yaroslavskyba.rozetka.repository;

import com.gitlab.yaroslavskyba.rozetka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUuid(UUID uuid);
    Optional<User> findUserByLogin(String login);
}
