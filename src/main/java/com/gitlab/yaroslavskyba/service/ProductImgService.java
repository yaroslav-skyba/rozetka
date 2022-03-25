package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.exception.ProductServiceException;

import java.util.UUID;

public interface ProductImgService {
    void createProductImg(UUID productUuid, String img) throws ProductServiceException;
    String getProductImgByUuid(UUID uuid) throws ProductServiceException;
    void updateProductImg(UUID productUuid, String img) throws ProductServiceException;
}
