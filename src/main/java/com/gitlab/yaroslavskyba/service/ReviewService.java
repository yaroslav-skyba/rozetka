package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.ReviewDto;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    void createReview(ReviewDto reviewDto) throws ReviewServiceException;
    ReviewDto getReviewByUuid(UUID uuid) throws ReviewServiceException;
    List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct) throws ReviewServiceException;
    List<ReviewDto> getReviewList() throws ReviewServiceException;
    void updateReviewByUuid(UUID uuid, ReviewDto reviewDto) throws ReviewServiceException;
    void deleteReviewByUuid(UUID uuid) throws ReviewServiceException;
}
