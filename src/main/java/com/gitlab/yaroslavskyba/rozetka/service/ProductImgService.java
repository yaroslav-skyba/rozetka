package com.gitlab.yaroslavskyba.rozetka.service;

import com.gitlab.yaroslavskyba.rozetka.exception.ProductImgServiceException;

import java.util.UUID;

public interface ProductImgService {
    void createProductImg(UUID productUuid, String img) throws ProductImgServiceException;
    String getProductImgByUuid(UUID uuid) throws ProductImgServiceException;
    void deleteProductImg(UUID productUuid) throws ProductImgServiceException;
}
