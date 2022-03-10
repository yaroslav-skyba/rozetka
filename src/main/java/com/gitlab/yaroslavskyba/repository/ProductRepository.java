package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductByUuid(UUID uuid);
    List<Product> findProductByName(String name);
}
