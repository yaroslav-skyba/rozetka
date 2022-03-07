package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;
import com.gitlab.yaroslavskyba.service.OrderItemService;
import com.gitlab.yaroslavskyba.service.OrderService;
import com.gitlab.yaroslavskyba.util.ControllerPath;
import com.gitlab.yaroslavskyba.util.MediaType;
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
@RequestMapping(value = ControllerPath.ORDERS, produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping(consumes = MediaType.ORDER)
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        try {
            orderService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("An order has been successfully created");
        } catch (OrderServiceException orderServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(orderServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.UUID, produces = MediaType.ORDER)
    public ResponseEntity<OrderDto> getOrder(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(orderService.getOrderByUuid(uuid));
        } catch (OrderServiceException orderServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = ControllerPath.ORDER_ITEMS, consumes = MediaType.ORDER_ITEM_LIST)
    public ResponseEntity<String> createOrderItemList(@PathVariable UUID uuid, @RequestBody List<OrderItemDto> orderItemDtoList) {
        try {
            orderService.getOrderByUuid(uuid);
            orderItemService.createOrderItemList(orderItemDtoList);

            return ResponseEntity.status(HttpStatus.CREATED).body("An order item list has been successfully created");
        } catch (OrderServiceException orderServiceException) {
            return ResponseEntity.notFound().build();
        } catch (OrderItemServiceException orderItemServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(orderItemServiceException.getMessage());
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value = ControllerPath.ORDER_ITEMS, produces = MediaType.ORDER_ITEM_LIST)
    public ResponseEntity<List<OrderItemDto>> getOrderItemList(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(orderItemService.getOrderItemList());
        } catch (OrderItemServiceException orderItemServiceException) {
            return ResponseEntity.notFound().build();
        }
    }
}
