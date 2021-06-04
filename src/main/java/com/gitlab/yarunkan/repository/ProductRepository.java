package com.gitlab.yarunkan.repository;

import com.gitlab.yarunkan.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> { }
