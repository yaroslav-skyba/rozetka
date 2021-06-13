package com.gitlab.yarunkan.dto;

import com.gitlab.yarunkan.configuration.PersistenceConfig;
import com.gitlab.yarunkan.repository.ProductRepository;
import com.gitlab.yarunkan.repository.ReviewRepository;
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
import javax.validation.ConstraintViolationException;

@Rollback
@Transactional
@ContextConfiguration(classes = {PersistenceConfig.class})
public class ReviewTestIT extends AbstractTestNGSpringContextTests {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private Review review1;
    private Review review2;

    @BeforeMethod(groups = {"integration-tests"})
    public void setUp() {
        final Product product = ITCreateEntityUtil.createProduct();
        productRepository.save(product);

        review1 = ITCreateEntityUtil.createReview(product);
        review2 = ITCreateEntityUtil.createReview(product);
    }

    @AfterMethod(groups = {"integration-tests"})
    public void tearDown() {
        productRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testReviewIsNotSavedWithEmptyContent() {
        review1.setContent("");
        reviewRepository.save(review1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testReviewIsNotSavedWithNullContent() {
        review1.setContent(null);
        reviewRepository.save(review1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testReviewIsNotSavedWithRatingLessThanOne() {
        review1.setRating(0);
        reviewRepository.save(review1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = ConstraintViolationException.class)
    public void testReviewIsNotSavedWithRatingMoreThanFive() {
        review1.setRating(6);
        reviewRepository.save(review1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testReviewIsNotSavedWithNullUUID() {
        review1.setUuid(null);
        reviewRepository.save(review1);
    }

    @Rollback
    @Transactional
    @Test(groups = {"integration-tests"}, expectedExceptions = DataIntegrityViolationException.class)
    public void testReviewIsNotSavedWithUniqueUUID() {
        reviewRepository.save(review1);

        review2.setUuid(review1.getUuid());
        reviewRepository.save(review2);
    }
}
