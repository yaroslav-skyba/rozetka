package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import com.gitlab.yaroslavskyba.service.OrderItemService;
import com.gitlab.yaroslavskyba.util.ControllerPath;
import com.gitlab.yaroslavskyba.util.MediaType;
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

    @SuppressWarnings("SpringElInspection")
    @PostMapping(value = ControllerPath.ORDERS, consumes = MediaType.ORDER_ITEM_LIST, produces = TEXT_PLAIN_VALUE)
    @PreAuthorize("#orderItemDtoList.?[#this.getUuidUser() == #root.principal.uuid].size() eq #orderItemDtoList.size()")
    public ResponseEntity<String> createOrderItemList(@RequestBody List<OrderItemDto> orderItemDtoList) {
        try {
            orderItemService.createOrderItemList(orderItemDtoList);
            return ResponseEntity.status(HttpStatus.CREATED).body("An order item list has been successfully created");
        } catch (OrderItemServiceException orderItemServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(orderItemServiceException.getMessage());
        }
    }
}
