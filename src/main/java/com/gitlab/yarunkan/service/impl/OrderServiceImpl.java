package com.gitlab.yarunkan.service.impl;

import com.gitlab.yarunkan.dto.OrderDto;
import com.gitlab.yarunkan.model.Order;
import com.gitlab.yarunkan.exception.OrderItemServiceException;
import com.gitlab.yarunkan.exception.OrderServiceException;
import com.gitlab.yarunkan.repository.OrderRepository;
import com.gitlab.yarunkan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    @NotNull
    public OrderDto create(OrderDto orderDto) {
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
    @Transactional
    @NotNull
    public OrderDto getByUuid(UUID uuid) throws OrderServiceException {
        try {
            final var order = orderRepository.findByUuid(uuid);

            return new OrderDto(order.getUuid(), order.getDescription());
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while getting an order", e);
        }
    }
}
