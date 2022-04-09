package com.gitlab.yaroslavskyba.rozetka.controller;

import com.gitlab.yaroslavskyba.rozetka.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.rozetka.exception.OrderServiceException;
import com.gitlab.yaroslavskyba.rozetka.service.OrderItemService;
import com.gitlab.yaroslavskyba.rozetka.util.ControllerPath;
import com.gitlab.yaroslavskyba.rozetka.util.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
public class OrderController {
    private final OrderItemService orderItemService;

    public OrderController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    
    @PostMapping(value = ControllerPath.ORDERS, consumes = MediaType.ORDER_ITEM_LIST, produces = TEXT_PLAIN_VALUE)
    @PreAuthorize("#orderItemDtoList.?[#this.getUuidUser() == #root.principal.uuid].size() eq #orderItemDtoList.size()")
    public ResponseEntity<String> createOrderItemList(@RequestBody List<OrderItemDto> orderItemDtoList) {
        try {
            orderItemService.createOrderItemList(orderItemDtoList);
            return ResponseEntity.status(HttpStatus.CREATED).body("An order item list has been successfully created");
        } catch (OrderServiceException orderServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(orderServiceException.getMessage());
        }
    }
}
