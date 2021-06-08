package com.gitlab.yarunkan.dto;

import com.gitlab.yarunkan.configutation.PersistenceTestConfig;
import com.gitlab.yarunkan.repository.OrderRepository;
import com.gitlab.yarunkan.util.ITCreateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Rollback
@Transactional
@ContextConfiguration(classes = {PersistenceTestConfig.class})
public class OrderTestIT extends AbstractTestNGSpringContextTests {
    @Autowired
    private OrderRepository orderRepository;

    private Order order1;
    private Order order2;

    @BeforeMethod(groups = {"integration-tests"})
    public void setUp() {
        order1 = ITCreateEntityUtil.getOrder();
        order2 = ITCreateEntityUtil.getOrder();
    }

    @AfterMethod(groups = {"integration-tests"})
    public void tearDown() {
        orderRepository.deleteAll();
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderIsNotSavedWithNullUUID() {
        order1.setUuid(null);
        orderRepository.save(order1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testOrderIsNotSavedWithUniqueUUID() {
        orderRepository.save(order1);

        order2.setUuid(order1.getUuid());
        orderRepository.save(order2);
    }
}
