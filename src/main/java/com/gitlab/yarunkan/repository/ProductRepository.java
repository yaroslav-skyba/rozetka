package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByUuid(UUID uuid);
    Optional<Product> findByNameProduct(String nameProduct);
}
