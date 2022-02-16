package com.gitlab.yaroslavskyba.model;

import com.gitlab.yaroslavskyba.config.JpaConfig;
import com.gitlab.yaroslavskyba.repository.OrderItemRepository;
import com.gitlab.yaroslavskyba.repository.OrderRepository;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.util.ITCreateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.validation.ConstraintViolationException;

@Rollback
@Transactional
@ContextConfiguration(classes = {JpaConfig.class})
public class OrderItemTestIT extends AbstractTestNGSpringContextTests {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    private OrderItem orderItem1;
    private OrderItem orderItem2;

    @BeforeMethod(groups = {"integration-tests"})
    public void setUp() {
        final Order order = ITCreateEntityUtil.createOrder();
        orderRepository.save(order);
        final Product product = ITCreateEntityUtil.createProduct();
        productRepository.save(product);

        orderItem1 = ITCreateEntityUtil.createOrderItem(order, product);
        orderItem2 = ITCreateEntityUtil.createOrderItem(order, product);
    }

    @AfterMethod(groups = {"integration-tests"})
    public void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        orderItemRepository.deleteAll();
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testOrderItemIsNotSavedWithPriceLessThanZero() {
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithNullPrice() {
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testOrderItemIsNotSavedWithQuantityLessThanZero() {
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithNullQuantity() {
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithNullUUID() {
        orderItem1.setUuid(null);
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithUniqueUUID() {
        orderItemRepository.save(orderItem1);

        orderItem2.setUuid(orderItem1.getUuid());
        orderItemRepository.save(orderItem2);
    }
}
