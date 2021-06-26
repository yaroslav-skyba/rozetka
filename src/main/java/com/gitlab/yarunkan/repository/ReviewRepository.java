package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findByUuid(UUID uuid);
    List<Review> findByProductUuid(UUID uuid);
}
