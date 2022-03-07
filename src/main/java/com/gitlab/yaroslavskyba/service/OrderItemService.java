package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import java.util.List;
import java.util.UUID;

public interface OrderItemService {
    void createOrderItem(OrderItemDto orderItemDto) throws OrderItemServiceException;
    void createOrderItemList(List<OrderItemDto> orderItemDtoList) throws OrderItemServiceException;
    OrderItemDto getOrderItemByUuid(UUID uuid) throws OrderItemServiceException;
    List<OrderItemDto> getOrderItemList() throws OrderItemServiceException;
}
