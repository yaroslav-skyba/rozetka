package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import com.gitlab.yaroslavskyba.model.OrderItem;
import com.gitlab.yaroslavskyba.model.Product;
import com.gitlab.yaroslavskyba.repository.OrderItemRepository;
import com.gitlab.yaroslavskyba.repository.OrderRepository;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.service.OrderItemService;
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

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository,
                                ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createOrderItem(OrderItemDto orderItemDto) {
        try {
            final int orderItemProductQuantity = 1;
            final Product product = productRepository.findProductByUuid(orderItemDto.getProductUuid()).orElseThrow();

            //Do not remove this variable
            final Integer productQuantity = product.getQuantity();

            product.setQuantity(orderItemProductQuantity);

            final OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderRepository.findOrderByUuid(orderItemDto.getOrderUuid()).orElseThrow());
            orderItem.setProduct(product);
            orderItem.setUuid(UUID.randomUUID());

            orderItemRepository.saveAndFlush(orderItem);

            product.setQuantity(productQuantity - orderItemProductQuantity);
            productRepository.saveAndFlush(product);
        } catch (Exception exception) {
            throw new OrderItemServiceException("An error occurred while creating an order item", exception);
        }
    }

    @Override
    public void createOrderItemList(List<OrderItemDto> orderItemDtoList) {
        try {
            orderItemDtoList.forEach(this::createOrderItem);
        } catch (Exception exception) {
            throw new OrderItemServiceException("An error occurred while creating an order item list", exception);
        }
    }

    @Override
    public OrderItemDto getOrderItemByUuid(UUID uuid) {
        try {
            final OrderItem orderItem = orderItemRepository.findOrderItemByUuid(uuid).orElseThrow();
            return new OrderItemDto(uuid, orderItem.getProduct().getUuid(), orderItem.getOrder().getUuid());
        } catch (Exception exception) {
            throw new OrderItemServiceException("An error occurred while getting an order item", exception);
        }
    }

    @Override
    public List<OrderItemDto> getOrderItemList() {
        try {
            final List<OrderItemDto> orderItemDtoList = new ArrayList<>();
            orderItemRepository.findAll().forEach(orderItem -> orderItemDtoList.add(getOrderItemByUuid(orderItem.getUuid())));

            return orderItemDtoList;
        } catch (Exception exception) {
            throw new OrderItemServiceException("An error occurred while getting an order item list", exception);
        }
    }
}
