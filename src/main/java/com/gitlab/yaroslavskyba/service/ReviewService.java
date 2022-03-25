package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.ReviewDto;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    void createReview(ReviewDto reviewDto, UUID uuidProduct) throws ReviewServiceException;
    List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct) throws ReviewServiceException;
    void updateReviewByUuid(UUID uuid, ReviewDto reviewDto, UUID uuidProduct) throws ReviewServiceException;
    void deleteReviewByUuid(UUID uuid, UUID uuidProduct) throws ReviewServiceException;
}
