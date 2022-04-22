package com.gitlab.yaroslavskyba.rozetka.controller;

import com.gitlab.yaroslavskyba.rozetka.dto.ProductDto;
import com.gitlab.yaroslavskyba.rozetka.dto.ReviewDto;
import com.gitlab.yaroslavskyba.rozetka.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.rozetka.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.rozetka.service.ProductService;
import com.gitlab.yaroslavskyba.rozetka.service.ReviewService;
import com.gitlab.yaroslavskyba.rozetka.util.ControllerPath;
import com.gitlab.yaroslavskyba.rozetka.util.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @PostMapping(value = ControllerPath.PRODUCTS, consumes = MediaType.PRODUCT)
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        try {
            productService.create(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("A product has been successfully created");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.PRODUCT, produces = MediaType.PRODUCT)
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(productService.get(uuid));
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = ControllerPath.PRODUCTS, produces = MediaType.PRODUCT_LIST)
    public ResponseEntity<List<ProductDto>> getProductList(@RequestParam(required = false) String name) {
        try {
            final List<ProductDto> productDtoList;

            if (name != null) {
                productDtoList = productService.getList(name);
            } else {
                productDtoList = productService.getList();
            }

            return ResponseEntity.ok(productDtoList);
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = ControllerPath.PRODUCTS, consumes = MediaType.PRODUCT)
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto productDto) {
        try {
            productService.update(productDto);
            return ResponseEntity.ok("A product has been successfully updated");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    @DeleteMapping(ControllerPath.PRODUCT)
    public ResponseEntity<String> deleteProduct(@PathVariable UUID uuid) {
        try {
            productService.delete(uuid);
            return ResponseEntity.noContent().build();
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    
    @PostMapping(value = ControllerPath.REVIEWS, consumes = MediaType.REVIEW)
    @PreAuthorize("principal.uuid.equals(#reviewDto.userUuid)")
    public ResponseEntity<String> createReview(@PathVariable UUID uuidProduct, @RequestBody ReviewDto reviewDto) {
        try {
            reviewService.createReview(reviewDto, uuidProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body("A review has been successfully created");
        } catch (ReviewServiceException reviewServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(reviewServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.REVIEWS, produces = MediaType.REVIEW_LIST)
    public ResponseEntity<List<ReviewDto>> getReviewList(@PathVariable UUID uuidProduct) {
        try {
            return ResponseEntity.ok(reviewService.getReviewListByProductUuid(uuidProduct));
        } catch (ReviewServiceException reviewServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = ControllerPath.REVIEWS, consumes = MediaType.REVIEW)
    @PreAuthorize("principal.reviewUuidList.contains(#reviewDto.uuid)")
    public ResponseEntity<String> updateReview(@PathVariable UUID uuidProduct, @RequestBody ReviewDto reviewDto) {
        try {
            reviewService.updateReviewByUuid(reviewDto, uuidProduct);
            return ResponseEntity.ok("A review has been successfully updated");
        } catch (ReviewServiceException reviewServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(reviewServiceException.getMessage());
        }
    }

    
    @DeleteMapping(ControllerPath.REVIEW)
    @PreAuthorize("principal.reviewUuidList.contains(#uuid)")
    public ResponseEntity<String> deleteReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuid) {
        try {
            reviewService.deleteReviewByUuid(uuid, uuidProduct);
            return ResponseEntity.noContent().build();
        } catch (ReviewServiceException reviewServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(reviewServiceException.getMessage());
        }
    }
}
