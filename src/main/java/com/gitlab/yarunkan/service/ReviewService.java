package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.ReviewDto;
import java.util.List;
import java.util.UUID;

public interface ReviewService {
    List<ReviewDto> getReviewList();
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto getByUuid(UUID uuid);
    ReviewDto updateByUuid(UUID uuid, ReviewDto reviewDto);
    void deleteByUuid(UUID uuid);
}
