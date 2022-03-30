package com.gitlab.yaroslavskyba.rozetka.repository;

import com.gitlab.yaroslavskyba.rozetka.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
