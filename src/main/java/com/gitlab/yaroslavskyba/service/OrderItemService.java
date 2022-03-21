package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;

import java.util.List;

public interface OrderItemService {
    void createOrderItemList(List<OrderItemDto> orderItemDtoList) throws OrderServiceException;
}
