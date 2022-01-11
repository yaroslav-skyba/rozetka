package com.gitlab.yaroslavskyba.service.impl;

import com.gitlab.yaroslavskyba.dto.ProductDto;
import com.gitlab.yaroslavskyba.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.model.Product;
import com.gitlab.yaroslavskyba.repository.ProductRepository;
import com.gitlab.yaroslavskyba.service.ProductService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
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
    public List<ProductDto> getProductList() {
        try {
            final List<Product> productList = productRepository.findAll();
            final List<ProductDto> productDtoList = new ArrayList<>();

            for (var product : productList) {
                productDtoList.add(getProductDtoByUuid(product.getUuid()));
            }

            return productDtoList;
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting product list", e);
        }
    }

    @Override
    public ProductDto getProductDtoByUuid(UUID uuid) {
        try {
            final Product product = productRepository.findByUuid(uuid);

            return createProductDto(product, uuid);
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting an product", e);
        }
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        try {
            final Product product = new Product();
            product.setDescription(productDto.getDescription());
            product.setDiscount(productDto.getDiscount());
            product.setNameProduct(product.getNameProduct());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setUuid(UUID.randomUUID());

            productRepository.saveAndFlush(product);

            final UUID uuid = product.getUuid();

            return createProductDto(product, uuid);
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while creating an product", e);
        }
    }

    private ProductDto createProductDto(Product product, UUID uuid) {
        final String name = product.getNameProduct();
        final Integer dtoQuantity = product.getQuantity();
        final Float dtoPrice = product.getPrice();
        final Integer dtoDiscount = product.getDiscount();
        final String dtoDescription = product.getDescription();

        return new ProductDto(uuid, name, dtoQuantity, dtoPrice, dtoDiscount, dtoDescription);
    }

    @Override
    public String getImageByUuidProduct(UUID uuidProduct) {
        try {
            final String imagePath = "C:\\Projects\\IdeaProjects\\PetProjects\\Rozetka\\src\\main\\resources\\" + uuidProduct + ".png";

            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(imagePath)));
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting an product image", e);
        }
    }

    @Override
    public List<ProductDto> getProductListByName(String name) {
        try {
            final List<Product> productList = productRepository.findByNameProduct(name);
            final List<ProductDto> productDtoList = new ArrayList<>();

            for (var product : productList) {
                productDtoList.add(getProductDtoByUuid(product.getUuid()));
            }

            return productDtoList;
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting an product dto list by name", e);
        }
    }

    @Override
    public boolean isProductExistByUuid(UUID uuid) {
        try {
            return productRepository.existsByUuid(uuid);
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while checking product existence by uuid", e);
        }
    }
}
