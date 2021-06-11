package com.gitlab.yarunkan.service.impl;

import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.dto.Review;
import com.gitlab.yarunkan.exception.OrderServiceException;
import com.gitlab.yarunkan.repository.ReviewRepository;
import com.gitlab.yarunkan.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getReviewList() {
        try {
            return reviewRepository.findAll();
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while getting review list", e);
        }
    }

    @Override
    public Review createReview(Product product, String content, Integer rating) {
        try {
            final Review review = new Review();
            review.setContent(content);
            review.setProduct(product);
            review.setRating(rating);
            review.setUuid(UUID.randomUUID());

            product.addReview(review);

            return reviewRepository.saveAndFlush(review);
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while creating a review", e);
        }
    }

    @Override
    public Review getByUuid(UUID uuid) {
        try {
            return reviewRepository.findByUuid(uuid);
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while getting a review", e);
        }
    }

    @Override
    public Review updateByUuid(UUID uuid, Review review) {
        try {
            final Review actualReview = getByUuid(uuid);
            actualReview.setContent(review.getContent());
            actualReview.setProduct(review.getProduct());
            actualReview.setRating(review.getRating());

            return reviewRepository.saveAndFlush(actualReview);
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while updating a review", e);
        }
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        try {
            reviewRepository.deleteByUuid(uuid);
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while deleting a review", e);
        }
    }
}