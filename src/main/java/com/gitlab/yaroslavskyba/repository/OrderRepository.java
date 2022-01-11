package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByUuid(UUID uuid);
}
