package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.ProductDto;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getProductList();
    ProductDto getProductDtoByUuid(UUID uuid);
    ProductDto createProduct(ProductDto productDto);
    String getImageByUuidProduct(UUID uuidProduct);
    List<ProductDto> getProductListByName(String name);
    boolean isProductExistByUuid(UUID uuid);
}