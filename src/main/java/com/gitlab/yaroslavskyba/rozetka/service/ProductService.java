package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.ProductDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ProductServiceException;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    void createProduct(ProductDto productDto) throws ProductServiceException;
    List<ProductDto> getProductListByName(String name) throws ProductServiceException;
    List<ProductDto> getProductList() throws ProductServiceException;
    void updateProductByUuid(ProductDto productDto) throws ProductServiceException;
    void deleteProductByUuid(UUID uuid) throws ProductServiceException;
}
