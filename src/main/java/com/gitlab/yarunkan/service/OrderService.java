package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.Order;
import com.gitlab.yarunkan.exception.OrderServiceException;
import java.util.UUID;

public interface OrderService {
    Order create(String description) throws OrderServiceException;
    Order getByUuid(UUID uuid) throws OrderServiceException;
}
