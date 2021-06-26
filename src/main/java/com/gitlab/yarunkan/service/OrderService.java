package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.OrderDto;
import com.gitlab.yarunkan.exception.OrderServiceException;
import java.util.UUID;

public interface OrderService {
    OrderDto create(OrderDto orderDto) throws OrderServiceException;
    OrderDto getByUuid(UUID uuid) throws OrderServiceException;
}
