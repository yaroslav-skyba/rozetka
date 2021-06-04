package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> { }
