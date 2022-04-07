package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.OrderItemDto;
import com.gitlab.yaroslavskyba.rozetka.exception.OrderServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.Order;
import com.gitlab.yaroslavskyba.rozetka.model.OrderItem;
import com.gitlab.yaroslavskyba.rozetka.model.Product;
import com.gitlab.yaroslavskyba.rozetka.repository.OrderItemRepository;
import com.gitlab.yaroslavskyba.rozetka.repository.ProductRepository;
import com.gitlab.yaroslavskyba.rozetka.repository.UserRepository;
import com.gitlab.yaroslavskyba.rozetka.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    @PersistenceContext
    private EntityManager entityManager;

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
            order.setDescription(orderItemDtoList.toString());

            for (OrderItemDto orderItemDto : orderItemDtoList) {
                final int orderItemProductQuantity = 1;

                final Product product = productRepository.findProductByUuid(orderItemDto.getUuidProduct()).orElseThrow();
                product.setQuantity(product.getQuantity() - orderItemProductQuantity);
                productRepository.saveAndFlush(product);

                entityManager.detach(product);
                product.setQuantity(orderItemProductQuantity);
                product.setPrice(BigDecimal.valueOf(product.getPrice().floatValue() * product.getDiscount().floatValue() / 100));

                final OrderItem orderItem = new OrderItem();
                orderItem.setUuid(UUID.randomUUID());
                orderItem.setProduct(product);
                orderItem.setUser(userRepository.findUserByUuid(orderItemDto.getUuidUser()).orElseThrow());
                orderItem.setOrder(order);
                orderItemRepository.saveAndFlush(orderItem);
            }
        } catch (Exception exception) {
            throw new OrderServiceException("An error occurred while creating an order item list", exception);
        }
    }
}
