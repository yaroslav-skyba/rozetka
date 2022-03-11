package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.ReviewDto;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.model.Review;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.repository.ReviewRepository;
import com.gitlab.yaroslavskyba.repository.UserRepository;
import com.gitlab.yaroslavskyba.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createReview(ReviewDto reviewDto, UUID uuidProduct) {
        try {
            final Review review = new Review();
            review.setUuid(UUID.randomUUID());
            setReviewFields(reviewDto, review, uuidProduct);

            productRepository.findProductByUuid(uuidProduct);
            reviewRepository.saveAndFlush(review);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while creating a review", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewDto getReviewByUuid(UUID uuid, UUID uuidProduct) {
        try {
            final Review review = reviewRepository.findReviewByUuid(uuid).orElseThrow();
            productRepository.findProductByUuid(uuidProduct).orElseThrow().setUuid(uuidProduct);

            return new ReviewDto(review.getUuid(), review.getUser().getUuid(), review.getContent(),
                                 review.getRating());
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a review", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct) {
        try {
            final List<ReviewDto> reviewDtoList = reviewRepository.findReviewsByProductUuid(uuidProduct).stream()
                .map(review -> getReviewByUuid(review.getUuid(), uuidProduct)).collect(Collectors.toList());

            if (reviewDtoList.isEmpty()) {
                throw new ReviewServiceException("A review list is empty");
            }

            return reviewDtoList;
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a review list", exception);
        }
    }

    @Override
    public void updateReviewByUuid(UUID uuid, ReviewDto reviewDto, UUID uuidProduct) {
        try {
            final Review review = reviewRepository.findReviewByUuid(uuid).orElseThrow();
            review.setUuid(reviewDto.getUuid());
            setReviewFields(reviewDto, review, uuidProduct);

            productRepository.findProductByUuid(uuidProduct);
            reviewRepository.saveAndFlush(review);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while updating a review", exception);
        }
    }

    @Override
    public void deleteReviewByUuid(UUID uuid, UUID uuidProduct) {
        try {
            productRepository.findProductByUuid(uuidProduct);
            reviewRepository.deleteReviewByUuid(uuid);
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while deleting a review", exception);
        }
    }

    private void setReviewFields(ReviewDto reviewDto, Review review, UUID uuidProduct) {
        review.setProduct(productRepository.findProductByUuid(uuidProduct).orElseThrow());
        review.setUser(userRepository.findUserByUuid(reviewDto.getUuidUser()).orElseThrow());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());
    }
}
