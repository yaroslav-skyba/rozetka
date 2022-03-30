package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.ReviewDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ReviewServiceException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    void createReview(ReviewDto reviewDto, UUID uuidProduct) throws ReviewServiceException;
    List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct) throws ReviewServiceException;
    void updateReviewByUuid(ReviewDto reviewDto, UUID uuid) throws ReviewServiceException;
    void deleteReviewByUuid(UUID uuid, UUID uuidProduct) throws ReviewServiceException;
}
