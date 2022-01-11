package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}