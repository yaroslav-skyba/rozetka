package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.ReviewDto;
import java.util.List;
import java.util.UUID;

public interface ReviewService {
    List<ReviewDto> getReviewList();
    List<ReviewDto> getReviewListByProductUuid(UUID uuidProduct);
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto getByUuid(UUID uuid);
    ReviewDto updateByUuid(UUID uuid, ReviewDto reviewDto);
    void deleteByUuid(UUID uuid);
}
