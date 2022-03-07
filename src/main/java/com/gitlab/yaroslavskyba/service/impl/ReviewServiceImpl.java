package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.ReviewDto;
import com.gitlab.yaroslavskyba.model.Review;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.repository.ReviewRepository;
import com.gitlab.yaroslavskyba.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createReview(ReviewDto reviewDto) {
        try {
            final Review review = new Review();
            review.setUuid(UUID.randomUUID());
            setReviewFields(reviewDto, review);

            reviewRepository.saveAndFlush(review);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while creating a review", exception);
        }
    }

    @Override
    public ReviewDto getReviewByUuid(UUID uuid) {
        try {
            final Review review = reviewRepository.findReviewByUuid(uuid).orElseThrow();
            return new ReviewDto(review.getUuid(), review.getProduct().getUuid(), review.getContent(), review.getRating());
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a review", exception);
        }
    }

    @Override
    public List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct) {
        try {
            final List<ReviewDto> reviewDtoList = new ArrayList<>();
            reviewRepository.findReviewByProductUuid(uuidProduct).forEach(review -> reviewDtoList.add(getReviewByUuid(review.getUuid())));

            return reviewDtoList;
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a review list", exception);
        }
    }

    @Override
    public List<ReviewDto> getReviewList() {
        try {
            final List<ReviewDto> reviewDtoList = new ArrayList<>();
            reviewRepository.findAll().forEach(review -> reviewDtoList.add(getReviewByUuid(review.getUuid())));

            return reviewDtoList;
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a review list", exception);
        }
    }

    @Override
    public void updateReviewByUuid(UUID uuid, ReviewDto reviewDto) {
        try {
            final Review review = reviewRepository.findReviewByUuid(uuid).orElseThrow();
            review.setUuid(reviewDto.getUuid());
            setReviewFields(reviewDto, review);

            reviewRepository.saveAndFlush(review);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while updating a review", exception);
        }
    }

    @Override
    public void deleteReviewByUuid(UUID uuid) {
        try {
            reviewRepository.deleteReviewByUuid(uuid);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while deleting a review", exception);
        }
    }

    private void setReviewFields(ReviewDto reviewDto, Review review) {
        review.setContent(reviewDto.getContent());
        review.setProduct(productRepository.findProductByUuid(reviewDto.getUuidProduct()).orElseThrow());
        review.setRating(reviewDto.getRating());
    }
}
