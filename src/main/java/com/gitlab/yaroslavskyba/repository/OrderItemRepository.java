package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
