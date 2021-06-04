package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByUuid(UUID uuid);
    Optional<Review> findByProductUuid(UUID uuidProduct);
}
