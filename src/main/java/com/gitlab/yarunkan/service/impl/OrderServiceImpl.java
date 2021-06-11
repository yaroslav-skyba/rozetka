package com.gitlab.yarunkan.service.impl;

import com.gitlab.yarunkan.dto.Order;
import com.gitlab.yarunkan.exception.OrderItemServiceException;
import com.gitlab.yarunkan.exception.OrderServiceException;
import com.gitlab.yarunkan.repository.OrderRepository;
import com.gitlab.yarunkan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(String description) {
        try {
            final Order order = new Order();
            order.setDescription(description);
            order.setUuid(UUID.randomUUID());

            return orderRepository.saveAndFlush(order);
        } catch (Exception e) {
            throw new OrderItemServiceException("An error occurred while saving an order", e);
        }
    }

    @Override
    public Order getByUuid(UUID uuid) throws OrderServiceException {
        try {
            return orderRepository.findByUuid(uuid);
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while getting an order", e);
        }
    }
}
