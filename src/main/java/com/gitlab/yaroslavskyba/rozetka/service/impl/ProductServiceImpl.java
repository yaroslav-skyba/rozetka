package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.ProductDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.Product;
import com.gitlab.yaroslavskyba.rozetka.repository.ProductRepository;
import com.gitlab.yaroslavskyba.rozetka.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void create(ProductDto productDto) {
        try {
            final Product product = new Product();
            product.setUuid(UUID.randomUUID());

            productRepository.saveAndFlush(setProductFields(productDto, product));
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while creating a product", exception);
        }
    }

    @Override
    public ProductDto get(UUID uuid) throws ProductServiceException {
        try {
            final Product product = productRepository.findProductByUuid(uuid).orElseThrow();

            return new ProductDto(product.getUuid(), product.getName(), product.getQuantity(), product.getPrice(), product.getDiscount(),
                                  product.getDescription(), product.getImg());
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product", exception);
        }
    }

    @Override
    public List<ProductDto> getList(String name) {
        try {
            return getProductDtoList(productRepository.findProductsByName(name));
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product list", exception);
        }
    }

    @Override
    public List<ProductDto> getList() {
        try {
            return getProductDtoList(productRepository.findAll());
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product list", exception);
        }
    }

    @Override
    @Transactional
    public void update(ProductDto productDto) {
        try {
            productRepository.saveAndFlush(setProductFields(productDto,
                                                            productRepository.findProductByUuid(productDto.getUuid()).orElseThrow()));
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while updating a product", exception);
        }
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        try {
            productRepository.deleteById(productRepository.findProductByUuid(uuid).orElseThrow().getIdProduct());
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while deleting a product", exception);
        }
    }

    private Product setProductFields(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setDescription(productDto.getDescription());
        product.setImg(productDto.getImg());

        return product;
    }

    private List<ProductDto> getProductDtoList(List<Product> productList) {
        final List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            if (product.getQuantity() > 0) {
                productDtoList.add(new ProductDto(product.getUuid(), product.getName(), product.getQuantity(), product.getPrice(),
                                                  product.getDiscount(), product.getDescription(), product.getImg()));
            }
        }

        if (productDtoList.isEmpty()) {
            throw new ProductServiceException("A product list is empty");
        }

        return productDtoList;
    }
}
