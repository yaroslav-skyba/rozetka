package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.Order;
import com.gitlab.yarunkan.dto.OrderItem;
import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.exception.OrderItemServiceException;

public interface OrderItemService {
    OrderItem create(Product product, Order order, Integer quantity, Float price) throws OrderItemServiceException;
}
