package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    Optional<OrderItem> findByUuid(UUID uuid);
    Optional<OrderItem> findByProductUuid(UUID uuidProduct);
    Optional<OrderItem> findByOrderUuid(UUID uuidOrder);
}
