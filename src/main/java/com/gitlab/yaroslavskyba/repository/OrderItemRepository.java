package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    Optional<OrderItem> findOrderItemByUuid(UUID uuid);
    List<OrderItem> findOrderItemsByOrderUuid(UUID orderUuid);
}
