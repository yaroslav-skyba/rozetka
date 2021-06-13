package com.gitlab.yarunkan.service;

import com.gitlab.yarunkan.dto.Product;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProductList();
    Product getProductByUuid(UUID uuid);
    Product createProduct(String description, Integer discount, String nameProduct, Float price, Integer quantity);
    BufferedImage getImageByUuidProduct(UUID uuidProduct);
}
