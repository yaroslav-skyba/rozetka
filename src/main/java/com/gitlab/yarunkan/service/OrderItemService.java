package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.OrderItemDto;
import com.gitlab.yarunkan.exception.OrderItemServiceException;
import java.util.List;

public interface OrderItemService {
    OrderItemDto create(OrderItemDto orderItemDto) throws OrderItemServiceException;
    List<OrderItemDto> createList(List<OrderItemDto> orderItemDtoList) throws OrderItemServiceException;
}
