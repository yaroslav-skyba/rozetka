package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.model.Order;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;
import com.gitlab.yaroslavskyba.repository.OrderRepository;
import com.gitlab.yaroslavskyba.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void createOrder(OrderDto orderDto) {
        try {
            final Order order = new Order();
            order.setDescription(orderDto.getDescription());
            order.setUuid(UUID.randomUUID());

            orderRepository.saveAndFlush(order);

            return new OrderDto(order.getUuid(), order.getDescription());
        } catch (Exception e) {
            throw new OrderItemServiceException("An error occurred while saving an order", e);
        }
    }

    @Override
    public OrderDto getOrderByUuid(UUID uuid) throws OrderServiceException {
        try {
            final Order order = orderRepository.findOrderByUuid(uuid);

            return new OrderDto(order.getUuid(), order.getDescription());
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while getting an order", e);
        }
    }
}
