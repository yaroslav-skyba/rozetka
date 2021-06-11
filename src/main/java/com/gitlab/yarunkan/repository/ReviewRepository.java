package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
