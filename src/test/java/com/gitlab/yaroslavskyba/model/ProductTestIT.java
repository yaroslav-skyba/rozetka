package com.gitlab.yaroslavskyba.model;

import com.gitlab.yaroslavskyba.config.JpaConfig;
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
public class ProductTestIT extends AbstractTestNGSpringContextTests {
    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeMethod(groups = {"integration-tests"})
    public void setUp() {
        product1 = ITCreateEntityUtil.createProduct();
        product2 = ITCreateEntityUtil.createProduct();
    }

    @AfterMethod(groups = {"integration-tests"})
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testProductIsNotSavedWithDiscountLessThanZero() {
        product1.setDiscount(-1);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testProductIsNotSavedWithDiscountMoreThanHundred() {
        product1.setDiscount(101);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testProductIsNotSavedWithEmptyName() {
        product1.setName("");
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testProductIsNotSavedWithNullName() {
        product1.setName(null);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testProductIsNotSavedWithPriceLessThanZero() {
        product1.setPrice(-1f);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testProductIsNotSavedNullPrice() {
        product1.setPrice(null);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testProductIsNotSavedWithQuantityLessThanZero() {
        product1.setQuantity(-1);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testProductIsNotSavedWithNullQuantity() {
        product1.setQuantity(null);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testProductIsNotSavedWithNullUUID() {
        product1.setUuid(null);
        productRepository.save(product1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testProductIsNotSavedWithUniqueUUID() {
        productRepository.save(product1);

        product2.setUuid(product1.getUuid());
        productRepository.save(product2);
    }
}
