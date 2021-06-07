package com.gitlab.yarunkan.dto;

import com.gitlab.yarunkan.configutation.PersistenceTestConfig;
import com.gitlab.yarunkan.repository.OrderItemRepository;
import com.gitlab.yarunkan.repository.OrderRepository;
import com.gitlab.yarunkan.repository.ProductRepository;
import com.gitlab.yarunkan.util.ITCreateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.validation.ConstraintViolationException;

@Rollback
@ContextConfiguration(classes = {PersistenceTestConfig.class})
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
        final Order order = ITCreateEntityUtil.getOrder();
        orderRepository.save(order);
        final Product product = ITCreateEntityUtil.getProduct();
        productRepository.save(product);

        orderItem1 = ITCreateEntityUtil.getOrderItem(order, product);
        orderItem2 = ITCreateEntityUtil.getOrderItem(order, product);
    }

    @AfterMethod(groups = {"integration-tests"})
    public void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        orderItemRepository.deleteAll();
    }

    @Rollback
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testOrderItemIsNotSavedWithPriceLessThanZero() {
        orderItem1.setPrice(-1f);
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithNullPrice() {
        orderItem1.setPrice(null);
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testOrderItemIsNotSavedWithQuantityLessThanZero() {
        orderItem1.setQuantity(-1);
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithNullQuantity() {
        orderItem1.setQuantity(null);
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithNullUUID() {
        orderItem1.setUuid(null);
        orderItemRepository.save(orderItem1);
    }

    @Rollback
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderItemIsNotSavedWithUniqueUUID() {
        orderItemRepository.save(orderItem1);

        orderItem2.setUuid(orderItem1.getUuid());
        orderItemRepository.save(orderItem2);
    }
}
