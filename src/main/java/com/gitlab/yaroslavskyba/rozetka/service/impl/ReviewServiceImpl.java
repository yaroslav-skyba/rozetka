package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.ReviewDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.Review;
import com.gitlab.yaroslavskyba.rozetka.model.User;
import com.gitlab.yaroslavskyba.rozetka.repository.ProductRepository;
import com.gitlab.yaroslavskyba.rozetka.repository.ReviewRepository;
import com.gitlab.yaroslavskyba.rozetka.repository.UserRepository;
import com.gitlab.yaroslavskyba.rozetka.service.ReviewService;
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

            reviewRepository.saveAndFlush(setReviewFields(reviewDto, review, uuidProduct));
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while creating a review", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct) {
        try {
            final List<ReviewDto> reviewDtoList = reviewRepository.findReviewsByProductUuid(uuidProduct).stream().map(review -> {
                final User user = review.getUser();

                return new ReviewDto(review.getUuid(), user.getUuid(), user.getFirstName() + " " + user.getLastName(),
                                     review.getContent(), review.getRating());
            }).collect(Collectors.toList());

            if (reviewDtoList.isEmpty()) {
                throw new ReviewServiceException("A review list is empty");
            }

            return reviewDtoList;
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while getting a review list", exception);
        }
    }

    @Override
    public void updateReviewByUuid(ReviewDto reviewDto, UUID uuid) {
        try {
            reviewRepository.saveAndFlush(
                setReviewFields(reviewDto, reviewRepository.findReviewByUuid(reviewDto.getUuid()).orElseThrow(), uuid));
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while updating a review", exception);
        }
    }

    @Override
    public void deleteReviewByUuid(UUID uuid, UUID uuidProduct) {
        try {
            reviewRepository.delete(reviewRepository.findReviewByUuid(uuid).orElseThrow());
        } catch (Exception exception) {
            throw new ReviewServiceException("An error occurred while deleting a review", exception);
        }
    }

    private Review setReviewFields(ReviewDto reviewDto, Review review, UUID uuidProduct) {
        review.setProduct(productRepository.findProductByUuid(uuidProduct).orElseThrow());
        review.setUser(userRepository.findUserByUuid(reviewDto.getUserUuid()).orElseThrow());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        return review;
    }
}
