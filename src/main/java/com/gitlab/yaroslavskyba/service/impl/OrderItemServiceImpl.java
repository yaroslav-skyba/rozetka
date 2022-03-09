package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.exception.OrderItemServiceException;
import com.gitlab.yaroslavskyba.model.Order;
import com.gitlab.yaroslavskyba.model.OrderItem;
import com.gitlab.yaroslavskyba.model.Product;
import com.gitlab.yaroslavskyba.repository.OrderItemRepository;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
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
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ProductRepository productRepository,
                                UserRepository userRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrderItemList(List<OrderItemDto> orderItemDtoList) {
        try {
            final Order order = new Order();
            order.setUuid(UUID.randomUUID());

            for (OrderItemDto orderItemDto : orderItemDtoList) {
                final int orderItemProductQuantity = 1;
                final Product product = productRepository.findProductByUuid(orderItemDto.getUuidProduct()).orElseThrow();

                //Do not inline this variable
                final Integer productQuantity = product.getQuantity();

                product.setQuantity(orderItemProductQuantity);

                final OrderItem orderItem = new OrderItem();
                orderItem.setUuid(UUID.randomUUID());
                orderItem.setProduct(product);
                orderItem.setUser(userRepository.findUserByUuid(orderItemDto.getUuidUser()).orElseThrow());
                orderItem.setOrder(order);

                orderItemRepository.saveAndFlush(orderItem);

                product.setQuantity(productQuantity - orderItemProductQuantity);
                productRepository.saveAndFlush(product);
            }
        } catch (Exception exception) {
            throw new OrderItemServiceException("An error occurred while creating an order item list", exception);
        }
    }

    @Override
    public OrderItemDto getOrderItemByUuid(UUID uuid) {
        try {
            final OrderItem orderItem = orderItemRepository.findOrderItemByUuid(uuid).orElseThrow();
            return new OrderItemDto(uuid, orderItem.getProduct().getUuid(), orderItem.getUser().getUuid());
        } catch (Exception exception) {
            throw new OrderItemServiceException("An error occurred while getting an order item", exception);
        }
    }

    @Override
    public List<OrderItemDto> getItemListByOrderUuid(UUID orderUuid) {
        try {
            final List<OrderItemDto> orderItemDtoList = new ArrayList<>();
            orderItemRepository.findOrderItemsByOrderUuid(orderUuid)
                .forEach(orderItem -> orderItemDtoList.add(getOrderItemByUuid(orderItem.getUuid())));

            return orderItemDtoList;
        } catch (Exception exception) {
            throw new OrderItemServiceException("An error occurred while getting an order item list", exception);
        }
    }
}
