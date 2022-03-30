package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.rozetka.exception.OrderServiceException;

import java.util.List;

public interface OrderItemService {
    void createOrderItemList(List<OrderItemDto> orderItemDtoList) throws OrderServiceException;
}
