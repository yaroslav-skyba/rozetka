package com.gitlab.yaroslavskyba.rozetka.service.impl;

import com.gitlab.yaroslavskyba.rozetka.dto.ProductDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.rozetka.model.Product;
import com.gitlab.yaroslavskyba.rozetka.repository.ProductRepository;
import com.gitlab.yaroslavskyba.rozetka.service.ProductService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static java.util.Base64.getDecoder;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private static final String IMG_FOLDER = "src/main/resources/img/";
    private static final String PNG = "png";
    private static final String PNG_EXTENSION = "." + PNG;

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public UUID createProduct(ProductDto productDto) {
        try {
            final Product product = new Product();
            product.setUuid(UUID.randomUUID());

            return productRepository.saveAndFlush(setProductFields(productDto, product)).getUuid();
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while creating a product", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductListByName(String name) {
        try {
            return getProductDtoList(productRepository.findProductsByName(name));
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product list", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductList() {
        try {
            return getProductDtoList(productRepository.findAll());
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while getting a product list", exception);
        }
    }

    @Override
    public void updateProductByUuid(ProductDto productDto) {
        try {
            productRepository.saveAndFlush(
                setProductFields(productDto, productRepository.findProductByUuid(productDto.getUuid()).orElseThrow()));
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while updating a product", exception);
        }
    }

    @Override
    public void deleteProductByUuid(UUID uuid) {
        try {
            productRepository.deleteById(productRepository.findProductByUuid(uuid).orElseThrow().getIdProduct());
            Files.delete(Path.of(IMG_FOLDER + uuid + PNG_EXTENSION));
        } catch (Exception exception) {
            throw new ProductServiceException("An error occurred while deleting a product", exception);
        }
    }

    private Product setProductFields(ProductDto productDto, Product product) throws IOException {
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setDescription(productDto.getDescription());

        try (var inputStream = new ByteArrayInputStream(getDecoder().decode(productDto.getImg().split(",")[1].replace("\"", "")))) {
            ImageIO.write(ImageIO.read(inputStream), PNG, new File(IMG_FOLDER + productDto.getUuid() + PNG_EXTENSION));
        }

        return product;
    }

    private List<ProductDto> getProductDtoList(List<Product> productList) throws IOException {
        final List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            productDtoList.add(new ProductDto(product.getUuid(), product.getName(), product.getQuantity(), product.getPrice(),
                                              product.getDiscount(), product.getDescription(),
                                              "data:image/png;base64," + Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(
                                                  new File(IMG_FOLDER + product.getUuid() + PNG_EXTENSION)))));
        }

        if (productDtoList.isEmpty()) {
            throw new ProductServiceException("A product list is empty");
        }

        return productDtoList;
    }
}
