package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;
import java.util.UUID;

public interface OrderService {
    void createOrder(OrderDto orderDto) throws OrderServiceException;
    OrderDto getOrderByUuid(UUID uuid) throws OrderServiceException;
}
