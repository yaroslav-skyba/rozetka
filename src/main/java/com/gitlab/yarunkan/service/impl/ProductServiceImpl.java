package com.gitlab.yarunkan.service.impl;

import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.exception.OrderServiceException;
import com.gitlab.yarunkan.repository.ProductRepository;
import com.gitlab.yarunkan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductList() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while getting product list", e);
        }
    }

    @Override
    public Product getProductByUuid(UUID uuid) {
        try {
            return productRepository.findByUuid(uuid);
        } catch (Exception e) {
            throw new OrderServiceException("An error occurred while getting an product", e);
        }
    }

    @Override
    public Product createProduct(String description, Integer discount, String nameProduct, Float price, Integer quantity) {
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
            throw new OrderServiceException("An error occurred while creating an product", e);
        }
    }
}
