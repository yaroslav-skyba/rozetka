package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.ReviewDto;
import com.gitlab.yaroslavskyba.model.Review;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.repository.ReviewRepository;
import com.gitlab.yaroslavskyba.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ReviewDto> getReviewList() {
        try {
            final List<Review> reviewList = reviewRepository.findAll();
            final List<ReviewDto> reviewDtoList = new ArrayList<>();

            for (var review : reviewList) {
                reviewDtoList.add(getByUuid(review.getUuid()));
            }

            return reviewDtoList;
        } catch (Exception e) {
            throw new ReviewServiceException("An error occurred while getting review list", e);
        }
    }

    @Override
    public List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct) {
        try {
            final var reviewList = reviewRepository.findByProductUuid(uuidProduct);
            final List<ReviewDto> reviewDtoList = new ArrayList<>();

            for (var review : reviewList) {
                reviewDtoList.add(getByUuid(review.getUuid()));
            }

            return reviewDtoList;
        } catch (Exception e) {
            throw new ReviewServiceException("An error occurred while getting review list by product uuid", e);
        }
    }

    @Override
    @Transactional
    public ReviewDto createReview(ReviewDto reviewDto) {
        try {
            final Review review = new Review();
            review.setContent(reviewDto.getContent());
            review.setProduct(productRepository.findByUuid(reviewDto.getUuidProduct()));
            review.setRating(reviewDto.getRating());
            review.setUuid(UUID.randomUUID());

            reviewRepository.saveAndFlush(review);

            return new ReviewDto(review.getUuid(), review.getProduct().getUuid(), review.getContent(), review.getRating());
        } catch (Exception e) {
            throw new ReviewServiceException("An error occurred while creating a review", e);
        }
    }

    @Override
    public ReviewDto getByUuid(UUID uuid) {
        try {
            final Review review = reviewRepository.findByUuid(uuid);

            return new ReviewDto(review.getUuid(), review.getProduct().getUuid(), review.getContent(), review.getRating());
        } catch (Exception e) {
            throw new ReviewServiceException("An error occurred while getting a review", e);
        }
    }

    @Override
    @Transactional
    public ReviewDto updateByUuid(UUID uuid, ReviewDto reviewDto) {
        try {
            final Review review = reviewRepository.findByUuid(uuid);
            review.setContent(reviewDto.getContent());
            review.setProduct(productRepository.findByUuid(reviewDto.getUuidProduct()));
            review.setRating(reviewDto.getRating());

            reviewRepository.saveAndFlush(review);

            return new ReviewDto(review.getUuid(), review.getProduct().getUuid(), review.getContent(), review.getRating());
        } catch (Exception e) {
            throw new ReviewServiceException("An error occurred while updating a review", e);
        }
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        try {
            final Review review = reviewRepository.findByUuid(uuid);
            reviewRepository.deleteById(review.getIdReview());
        } catch (Exception e) {
            throw new ReviewServiceException("An error occurred while deleting a review", e);
        }
    }
}
