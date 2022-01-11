package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;
import java.util.UUID;

public interface OrderService {
    OrderDto create(OrderDto orderDto) throws OrderServiceException;
    OrderDto getByUuid(UUID uuid) throws OrderServiceException;
}
