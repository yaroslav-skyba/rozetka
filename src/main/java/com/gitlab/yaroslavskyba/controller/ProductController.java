package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.dto.ProductDto;
import com.gitlab.yaroslavskyba.dto.ReviewDto;
import com.gitlab.yaroslavskyba.exception.ProductServiceException;
import com.gitlab.yaroslavskyba.exception.ReviewServiceException;
import com.gitlab.yaroslavskyba.service.ProductService;
import com.gitlab.yaroslavskyba.service.ReviewService;
import com.gitlab.yaroslavskyba.util.ControllerPath;
import com.gitlab.yaroslavskyba.util.MediaType;
import com.gitlab.yaroslavskyba.util.RoleName;
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
            productService.createProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("A product has been successfully created");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.PRODUCT, produces = MediaType.PRODUCT)
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(productService.getProductByUuid(uuid));
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = ControllerPath.PRODUCT_IMAGE, produces = org.springframework.http.MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<String> getProductImage(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(productService.getProductImageByUuid(uuid));
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = ControllerPath.PRODUCTS, produces = MediaType.PRODUCT_LIST)
    public ResponseEntity<List<ProductDto>> getProductList(@RequestParam(required = false) String name) {
        try {
            final List<ProductDto> productDtoList;

            if (name != null) {
                productDtoList = productService.getProductListByName(name);
            } else {
                productDtoList = productService.getProductList();
            }

            return ResponseEntity.ok(productDtoList);
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = ControllerPath.PRODUCT, consumes = MediaType.PRODUCT)
    public ResponseEntity<String> updateProduct(@PathVariable UUID uuid, @RequestBody ProductDto productDto) {
        try {
            productService.updateProductByUuid(uuid, productDto);
            return ResponseEntity.ok("A product has been successfully updated");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    @DeleteMapping(ControllerPath.PRODUCT)
    public ResponseEntity<String> deleteProduct(@PathVariable UUID uuid) {
        try {
            productService.deleteProductByUuid(uuid);
            return ResponseEntity.ok("A product has been successfully deleted");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    @PostMapping(value = ControllerPath.REVIEWS, consumes = MediaType.REVIEW)
    public ResponseEntity<String> createReview(@PathVariable UUID uuidProduct, @RequestBody ReviewDto reviewDto) {
        try {
            productService.getProductByUuid(uuidProduct);
            reviewService.createReview(reviewDto);

            return ResponseEntity.status(HttpStatus.CREATED).body("A product has been successfully created");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        } catch (ReviewServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    @GetMapping(value = ControllerPath.REVIEW, produces = MediaType.REVIEW)
    public ResponseEntity<ReviewDto> getReview(@SuppressWarnings("unused") @PathVariable UUID uuidProduct, @PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(reviewService.getReviewByUuid(uuid));
        } catch (ReviewServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = ControllerPath.REVIEWS, produces = MediaType.REVIEW_LIST)
    public ResponseEntity<List<ReviewDto>> getReviewList(@PathVariable UUID uuidProduct) {
        try {
            return ResponseEntity.ok(reviewService.getReviewListByProductUuid(uuidProduct));
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        }
    }

    @SuppressWarnings({"ELValidationInJSP", "SpringElInspection"})
    @PutMapping(value = ControllerPath.REVIEW, consumes = MediaType.REVIEW)
    @PreAuthorize("principal.reviewUuidList.contains(#uuid) or hasAuthority('admin')")
    public ResponseEntity<String> updateReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuid, @RequestBody ReviewDto reviewDto) {
        try {
            productService.getProductByUuid(uuidProduct);
            reviewService.updateReviewByUuid(uuid, reviewDto);

            return ResponseEntity.ok("A review has been successfully updated");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        } catch (ReviewServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }

    @SuppressWarnings({"ELValidationInJSP", "SpringElInspection"})
    @DeleteMapping(ControllerPath.REVIEW)
    @PreAuthorize("principal.reviewUuidList.contains(#uuid) or hasAuthority(" + RoleName.ADMIN + ")")
    public ResponseEntity<String> deleteReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuid) {
        try {
            productService.getProductByUuid(uuidProduct);
            reviewService.deleteReviewByUuid(uuid);

            return ResponseEntity.ok("A review has been successfully deleted");
        } catch (ProductServiceException productServiceException) {
            return ResponseEntity.notFound().build();
        } catch (ReviewServiceException productServiceException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productServiceException.getMessage());
        }
    }
}
