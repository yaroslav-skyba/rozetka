package com.gitlab.yaroslavskyba.controller;

import com.gitlab.yaroslavskyba.util.MediaType;
import com.gitlab.yaroslavskyba.dto.ProductDto;
import com.gitlab.yaroslavskyba.dto.ReviewDto;
import com.gitlab.yaroslavskyba.service.ProductService;
import com.gitlab.yaroslavskyba.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/v1")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @PostMapping(value = "products", consumes = MediaType.PRODUCT)
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @GetMapping(value = "products", produces = MediaType.PRODUCT_LIST)
    public ResponseEntity<List<ProductDto>> getProductList(@RequestParam(required = false) String name) {
        final List<ProductDto> productDtoList;

        if (name != null) {
            productDtoList = productService.getProductListByName(name);
        } else {
            productDtoList = productService.getProductList();
        }

        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping(value = "products/{uuid}", produces = MediaType.PRODUCT)
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID uuid) {
        if (!productService.isProductExistByUuid(uuid)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productService.getProductByUuid(uuid));
    }

    @GetMapping(value = "products/{uuid}/reviews", produces = MediaType.REVIEW_LIST)
    public ResponseEntity<List<ReviewDto>> getReviewList(@PathVariable UUID uuid) {
        return ResponseEntity.ok(reviewService.getReviewListByProductUuid(uuid));
    }

    @PostMapping(value = "products/{uuid}/reviews", consumes = MediaType.REVIEW)
    public ResponseEntity<ReviewDto> addReview(@PathVariable UUID uuid, @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(reviewDto));
    }

    @GetMapping(value = "products/{uuidProduct}/reviews/{uuidReview}", produces = MediaType.REVIEW)
    public ResponseEntity<ReviewDto> getReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview) {
        return ResponseEntity.ok(reviewService.getReviewByUuid(uuidReview));
    }

    @PutMapping(value = "products/{uuidProduct}/reviews/{uuidReview}", consumes = MediaType.REVIEW)
    public ResponseEntity<ReviewDto> updateReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview,
                                                  @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.updateReviewByUuid(uuidReview, reviewDto));
    }

    @DeleteMapping(value = "products/{uuidProduct}/reviews/{uuidReview}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview) {
        reviewService.deleteReviewByUuid(uuidReview);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "products/{uuidProduct}/image", produces = "image/png")
    public ResponseEntity<String> getImage(@PathVariable UUID uuidProduct) {
        return ResponseEntity.ok(productService.getProductImageByUuid(uuidProduct));
    }
}
