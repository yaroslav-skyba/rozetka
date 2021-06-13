package com.gitlab.yarunkan.controller;

import com.gitlab.yarunkan.dto.Order;
import com.gitlab.yarunkan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/orders", consumes = "application/json")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(order.getDescription()));
    }

    @GetMapping(value = "orders/{uuid}", produces = "application/json")
    public ResponseEntity<Order> getOrder(@PathVariable UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getByUuid(uuid));
    }
}
