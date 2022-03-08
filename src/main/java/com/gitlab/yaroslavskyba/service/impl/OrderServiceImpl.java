package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;
import com.gitlab.yaroslavskyba.model.Order;
import com.gitlab.yaroslavskyba.repository.OrderRepository;
import com.gitlab.yaroslavskyba.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(OrderDto orderDto) {
        try {
            final Order order = new Order();
            order.setDescription(orderDto.getDescription());
            order.setUuid(UUID.randomUUID());

            orderRepository.saveAndFlush(order);
        } catch (Exception exception) {
            throw new OrderServiceException("An error occurred while saving an order", exception);
        }
    }

    @Override
    public OrderDto getOrderByUuid(UUID uuid) {
        try {
            final Order order = orderRepository.findOrderByUuid(uuid).orElseThrow();
            return new OrderDto(order.getUuid(), order.getUser().getUuid(), order.getDescription());
        } catch (Exception exception) {
            throw new OrderServiceException("An error occurred while getting an order", exception);
        }
    }
}
