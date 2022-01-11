package com.gitlab.yaroslavskyba.repository;

import com.gitlab.yaroslavskyba.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByUuid(UUID uuid);
    List<Product> findByNameProduct(String name);
    boolean existsByUuid(UUID uuid);
}
