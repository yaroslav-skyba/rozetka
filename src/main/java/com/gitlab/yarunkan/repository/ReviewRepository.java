package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> { }
