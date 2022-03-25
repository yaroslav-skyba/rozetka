package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.dto.ProductDto;
import com.gitlab.yaroslavskyba.exception.ProductServiceException;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    void createProduct(ProductDto productDto) throws ProductServiceException;
    List<ProductDto> getProductListByName(String name) throws ProductServiceException;
    List<ProductDto> getProductList() throws ProductServiceException;
    void updateProductByUuid(UUID uuid, ProductDto productDto) throws ProductServiceException;
    void deleteProductByUuid(UUID uuid) throws ProductServiceException;
}
