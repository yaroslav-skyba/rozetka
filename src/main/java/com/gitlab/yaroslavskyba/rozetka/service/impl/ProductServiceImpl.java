package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.ProductDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.Product;
import com.gitlab.yaroslavskyba.rozetka.repository.ProductRepository;
import com.gitlab.yaroslavskyba.rozetka.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public UUID createProduct(ProductDto productDto) {
        try {
            final Product product = new Product();
            product.setUuid(UUID.randomUUID());
            setProductFields(productDto, product);

            return productRepository.saveAndFlush(product).getUuid();
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while creating a product", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductListByName(String name) {
        try {
            final List<ProductDto> productDtoList =
                productRepository.findProductsByName(name).stream().map(product -> getProduct(product.getUuid()))
                    .collect(Collectors.toList());

            if (productDtoList.isEmpty()) {
                throw new ProductServiceException("A product list is empty");
            }

            return productDtoList;
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product list", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductList() {
        try {
            final List<ProductDto> productDtoList =
                productRepository.findAll().stream().map(product -> getProduct(product.getUuid())).collect(Collectors.toList());

            if (productDtoList.isEmpty()) {
                throw new ProductServiceException("A product list is empty");
            }

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
            productRepository.deleteById(productRepository.findProductByUuid(uuid).orElseThrow().getIdProduct());
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while deleting a product", exception);
        }
    }

    private void setProductFields(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setDescription(productDto.getDescription());
    }

    private ProductDto getProduct(UUID uuid) {
        final Product product = productRepository.findProductByUuid(uuid).orElseThrow();
        return new ProductDto(product.getUuid(), product.getName(), product.getQuantity(), product.getPrice(), product.getDiscount(),
                              product.getDescription());
    }
}
