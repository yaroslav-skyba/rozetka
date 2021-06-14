package com.gitlab.yarunkan.service.impl;

import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.exception.ProductServiceException;
import com.gitlab.yarunkan.repository.ProductRepository;
import com.gitlab.yarunkan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<Product> getProductList() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting product list", e);
        }
    }

    @Override
    @Transactional
    @NotNull
    public Product getProductByUuid(UUID uuid) {
        try {
            return productRepository.findByUuid(uuid);
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting an product", e);
        }
    }

    @Override
    @Transactional
    public Product createProduct(String description, Integer discount, @NotNull String nameProduct, @NotNull Float price, @NotNull Integer quantity) {
        try {
            final Product product = new Product();
            product.setDescription(description);
            product.setDiscount(discount);
            product.setNameProduct(nameProduct);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setUuid(UUID.randomUUID());

            return productRepository.saveAndFlush(product);
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while creating an product", e);
        }
    }

    @Override
    @Transactional
    @NotNull
    public BufferedImage getImageByUuidProduct(UUID uuidProduct) {
        try {
            return ImageIO.read(new File("../resources/" + uuidProduct + ".png"));
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting an product image", e);
        }
    }
}
