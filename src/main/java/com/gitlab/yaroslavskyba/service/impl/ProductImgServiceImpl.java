package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.exception.ProductImgServiceException;
import com.gitlab.yaroslavskyba.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.service.ProductImgService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
public class ProductImgServiceImpl implements ProductImgService {
    @Override
    public void createProductImg(UUID productUuid, String img) throws ProductServiceException {

    }

    @Override
    @Transactional(readOnly = true)
    public String getProductImgByUuid(UUID uuid) throws ProductServiceException {
        try {
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File("src/main/resources/img/" + uuid + ".png")));
        } catch (Exception exception) {
            throw new ProductImgServiceException("An error occurred while getting a product image", exception);
        }
    }

    @Override
    public void updateProductImg(UUID productUuid, String img) throws ProductServiceException {

    }
}
