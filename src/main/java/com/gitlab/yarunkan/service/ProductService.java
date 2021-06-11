package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.Product;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProductList();
    Product getProductByUuid(UUID uuid);
    Product createProduct(String description, Integer discount, String nameProduct, Float price, Integer quantity);
}
