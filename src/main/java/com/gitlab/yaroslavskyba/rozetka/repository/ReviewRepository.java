package com.gitlab.yaroslavskyba.rozetka.repository;

import com.gitlab.yaroslavskyba.rozetka.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findReviewByUuid(UUID uuid);
    List<Review> findReviewsByProductUuid(UUID productUuid);
}
