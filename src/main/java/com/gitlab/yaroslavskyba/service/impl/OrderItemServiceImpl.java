package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.model.OrderItem;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import com.gitlab.yaroslavskyba.repository.OrderItemRepository;
import com.gitlab.yaroslavskyba.repository.OrderRepository;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderItemDto create(OrderItemDto orderItemDto) {
        try {
            final UUID orderUuid = orderItemDto.getOrderUuid();
            final Float price = orderItemDto.getPrice();
            final UUID productUuid = orderItemDto.getProductUuid();
            final UUID uuid = UUID.randomUUID();

            final OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderRepository.findByUuid(orderUuid));
            orderItem.setPrice(price);
            orderItem.setProduct(productRepository.findByUuid(productUuid));
            orderItem.setUuid(uuid);

            orderItemRepository.saveAndFlush(orderItem);

            return new OrderItemDto(uuid, productUuid, orderUuid, price);
        } catch (Exception e) {
            throw new OrderItemServiceException("An error occurred while saving an order item", e);
        }
    }

    @Override
    public List<OrderItemDto> createList(List<OrderItemDto> orderItemDtoList) throws OrderItemServiceException {
        try {
            final List<OrderItemDto> orderItemDtoListResponse = new ArrayList<>();

            for (var orderItemDto : orderItemDtoList) {
                orderItemDtoListResponse.add(create(orderItemDto));
            }

            return orderItemDtoListResponse;
        } catch (Exception e) {
            throw new OrderItemServiceException("An error occurred while saving an order item list", e);
        }
    }
}
