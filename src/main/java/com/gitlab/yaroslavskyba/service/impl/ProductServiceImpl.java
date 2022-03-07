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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getProductList() {
        try {
            final List<Product> productList = productRepository.findAll();
            final List<ProductDto> productDtoList = new ArrayList<>();

            for (Product product : productList) {
                productDtoList.add(getProductByUuid(product.getUuid()));
            }

            return productDtoList;
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting a product list", e);
        }
    }

    @Override
    public ProductDto getProductByUuid(UUID uuid) {
        try {
            return createProductDto(productRepository.findProductByUuid(uuid), uuid);
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting a product", e);
        }
    }

    @Override
    @Transactional
    public void createProduct(ProductDto productDto) {
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
            throw new ProductServiceException("An error occurred while creating a product", e);
        }
    }

    @Override
    public String getImageByUuidProduct(UUID uuidProduct) {
        try {
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(
                new File("C:\\Projects\\IdeaProjects\\PetProjects\\Rozetka\\src\\main\\resources\\" + uuidProduct + ".png")));
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting a product image", e);
        }
    }

    @Override
    public List<ProductDto> getProductListByName(String name) {
        try {
            final List<Product> productList = productRepository.findByNameProduct(name);
            final List<ProductDto> productDtoList = new ArrayList<>();

            for (var product : productList) {
                productDtoList.add(getProductByUuid(product.getUuid()));
            }

            return productDtoList;
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while getting a product list by a name", e);
        }
    }

    @Override
    public boolean isProductExistByUuid(UUID uuid) {
        try {
            return productRepository.existsProductByUuid(uuid);
        } catch (Exception e) {
            throw new ProductServiceException("An error occurred while checking a product existence by uuid", e);
        }
    }

    private ProductDto createProductDto(Product product, UUID uuid) {
        return new ProductDto(uuid, product.getNameProduct(), product.getQuantity(), product.getPrice(), product.getDiscount(),
                              product.getDescription());
    }
}
