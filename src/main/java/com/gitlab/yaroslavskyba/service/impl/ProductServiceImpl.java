package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.ProductDto;
import com.gitlab.yaroslavskyba.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.model.Product;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.service.ProductService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(ProductDto productDto) {
        try {
            final Product product = new Product();
            product.setUuid(UUID.randomUUID());
            setProductFields(productDto, product);

            productRepository.saveAndFlush(product);
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while creating a product", exception);
        }
    }

    @Override
    public ProductDto getProductByUuid(UUID uuid) {
        try {
            final Product product = productRepository.findProductByUuid(uuid).orElseThrow();
            return new ProductDto(product.getUuid(), product.getNameProduct(), product.getQuantity(), product.getPrice(),
                                  product.getDiscount(), product.getDescription());
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product", exception);
        }
    }

    @Override
    public String getProductImageByUuid(UUID uuid) {
        try {
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(
                new File("C:/Projects/IdeaProjects/PetProjects/Rozetka/src/main/resources/" + uuid + ".png")));
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product image", exception);
        }
    }

    @Override
    public List<ProductDto> getProductListByName(String name) {
        try {
            final List<ProductDto> productDtoList = new ArrayList<>();
            productRepository.findByNameProduct(name).forEach(product -> productDtoList.add(getProductByUuid(product.getUuid())));

            return productDtoList;
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product list", exception);
        }
    }

    @Override
    public List<ProductDto> getProductList() {
        try {
            final List<ProductDto> productDtoList = new ArrayList<>();
            productRepository.findAll().forEach(product -> productDtoList.add(getProductByUuid(product.getUuid())));

            return productDtoList;
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product list", exception);
        }
    }

    @Override
    public void updateProductByUuid(UUID uuid, ProductDto productDto) {
        try {
            final Product product = productRepository.findProductByUuid(uuid).orElseThrow();
            product.setUuid(productDto.getUuid());
            setProductFields(productDto, product);

            productRepository.saveAndFlush(product);
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while updating a product", exception);
        }
    }

    @Override
    public void deleteProductByUuid(UUID uuid) {
        try {
            productRepository.deleteProductByUuid(uuid);
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while deleting a product", exception);
        }
    }

    private void setProductFields(ProductDto productDto, Product product) {
        product.setDescription(productDto.getDescription());
        product.setDiscount(productDto.getDiscount());
        product.setNameProduct(product.getNameProduct());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
    }
}
