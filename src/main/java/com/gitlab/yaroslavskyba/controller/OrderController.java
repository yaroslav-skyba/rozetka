package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.dto.OrderDto;
import com.gitlab.yaroslavskyba.exception.OrderServiceException;
import com.gitlab.yaroslavskyba.service.OrderService;
import com.gitlab.yaroslavskyba.util.ControllerPath;
import com.gitlab.yaroslavskyba.util.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = ControllerPath.ORDERS, produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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

    @SuppressWarnings({"ELValidationInJSP", "SpringElInspection"})
    @GetMapping(value = ControllerPath.UUID, produces = MediaType.ORDER)
    @PreAuthorize("principal.orderUuidList.contains(#uuid) or hasAuthority('admin')")
    public ResponseEntity<OrderDto> getOrder(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(orderService.getOrderByUuid(uuid));
        } catch (OrderServiceException orderServiceException) {
            return ResponseEntity.notFound().build();
        }
    }
}
