package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import java.util.List;

public interface OrderItemService {
    OrderItemDto create(OrderItemDto orderItemDto) throws OrderItemServiceException;
    List<OrderItemDto> createList(List<OrderItemDto> orderItemDtoList) throws OrderItemServiceException;
}
