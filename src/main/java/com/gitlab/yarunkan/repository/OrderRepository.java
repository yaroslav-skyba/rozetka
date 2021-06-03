package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByUuid(UUID uuid);
}
