package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByUuid(UUID uuid);
}
