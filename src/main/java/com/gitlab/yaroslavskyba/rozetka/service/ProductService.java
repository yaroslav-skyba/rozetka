package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.dto.ProductDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ProductServiceException;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    void create(ProductDto productDto) throws ProductServiceException;
    ProductDto get(UUID uuid) throws ProductServiceException;
    List<ProductDto> getList(String name) throws ProductServiceException;
    List<ProductDto> getList() throws ProductServiceException;
    void update(ProductDto productDto) throws ProductServiceException;
    void delete(UUID uuid) throws ProductServiceException;
}
