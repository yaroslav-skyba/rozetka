package com.gitlab.yarunkan.service.impl;

import com.gitlab.yarunkan.dto.Order;
import com.gitlab.yarunkan.dto.OrderItem;
import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.exception.OrderItemServiceException;
import com.gitlab.yarunkan.repository.OrderItemRepository;
import com.gitlab.yarunkan.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem create(Product product, Order order, Integer quantity, Float price) throws OrderItemServiceException {
        try {
            final OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(price);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setUuid(UUID.randomUUID());

            return orderItemRepository.saveAndFlush(orderItem);
        } catch (Exception e) {
            throw new OrderItemServiceException("An error occurred while saving an order item", e);
        }
    }
}
