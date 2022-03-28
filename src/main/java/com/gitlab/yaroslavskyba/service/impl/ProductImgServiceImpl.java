package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.exception.ProductImgServiceException;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.service.ProductImgService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
public class ProductImgServiceImpl implements ProductImgService {
    private static final String IMG_FOLDER = "src/main/resources/img/";
    private static final String PNG = "png";
    private static final String PNG_EXTENSION = "." + PNG;

    private final ProductRepository productRepository;

    public ProductImgServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProductImg(UUID productUuid, String img) {
        try (InputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(img.split(",")[1].replace("\"", "")))) {
            productRepository.findProductByUuid(productUuid).orElseThrow().setUuid(productUuid);
            ImageIO.write(ImageIO.read(inputStream), PNG, new File(IMG_FOLDER + productUuid + PNG_EXTENSION));
        } catch (Exception exception) {
            throw new ProductImgServiceException("An error occurred while creating a product image", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getProductImgByUuid(UUID uuid) {
        try {
            return "data:image/png;base64," + Base64.getEncoder()
                .encodeToString(FileUtils.readFileToByteArray(new File(IMG_FOLDER + uuid + PNG_EXTENSION)));
        } catch (Exception exception) {
            throw new ProductImgServiceException("An error occurred while getting a product image", exception);
        }
    }

    @Override
    public void deleteProductImg(UUID productUuid) {
        try {
            Files.delete(Path.of(IMG_FOLDER + productUuid + PNG_EXTENSION));
        } catch (Exception exception) {
            throw new ProductImgServiceException("An error occurred deleting getting a product image", exception);
        }
    }
}
