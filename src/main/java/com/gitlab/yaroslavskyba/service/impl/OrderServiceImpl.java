package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;
import com.gitlab.yaroslavskyba.model.Order;
import com.gitlab.yaroslavskyba.repository.OrderRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrder(OrderDto orderDto) {
        try {
            final Order order = new Order();
            order.setUuid(UUID.randomUUID());
            order.setDescription(orderDto.getDescription());

            orderRepository.saveAndFlush(order);
        } catch (Exception exception) {
            throw new OrderServiceException("An error occurred while saving an order", exception);
        }
    }

    @Override
    public OrderDto getOrderByUuid(UUID uuid) {
        try {
            final Order order = orderRepository.findOrderByUuid(uuid).orElseThrow();
            return new OrderDto(order.getUuid(), order.getDescription());
        } catch (Exception exception) {
            throw new OrderServiceException("An error occurred while getting an order", exception);
        }
    }
}
