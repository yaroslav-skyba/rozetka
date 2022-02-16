package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.MediaType;
import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import com.gitlab.yaroslavskyba.service.OrderItemService;
import com.gitlab.yaroslavskyba.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping(value = "orders", consumes = MediaType.ORDER)
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderDto));
    }

    @GetMapping(value = "orders/{uuid}", produces = MediaType.ORDER)
    public ResponseEntity<OrderDto> getOrder(@PathVariable UUID uuid) {
        return ResponseEntity.ok(orderService.getByUuid(uuid));
    }

    @PostMapping(value = "orders/{uuid}/items", consumes = MediaType.ORDER_ITEM_LIST)
    public ResponseEntity<List<OrderItemDto>> addOrderItemList(@PathVariable UUID uuid, @RequestBody List<OrderItemDto> orderItemDtoList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.createList(orderItemDtoList));
        } catch (OrderItemServiceException orderItemServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of());
        }
    }
}
