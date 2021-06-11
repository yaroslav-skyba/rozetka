package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.dto.Review;
import java.util.List;
import java.util.UUID;

public interface ReviewService {
    List<Review> getReviewList();
    Review createReview(Product product, String content, Integer rating);
    Review getByUuid(UUID uuid);
    Review updateByUuid(UUID uuid, Review review);
    void deleteByUuid(UUID uuid);
}
