package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> { }
