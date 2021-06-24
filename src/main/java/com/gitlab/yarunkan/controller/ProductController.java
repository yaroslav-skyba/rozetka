package com.gitlab.yarunkan.controller;

import com.gitlab.yarunkan.controller.util.MediaType;
import com.gitlab.yarunkan.dto.ProductDto;
import com.gitlab.yarunkan.dto.ReviewDto;
import com.gitlab.yarunkan.service.ProductService;
import com.gitlab.yarunkan.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    @Autowired
    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/api/v1/products", produces = MediaType.PRODUCT_LIST)
    public ResponseEntity<List<ProductDto>> getProductList(@RequestParam(required = false) String name) {
        final List<ProductDto> productDtoList;

        if (name != null) {
            productDtoList = productService.getProductListByName(name);
        } else {
            productDtoList = productService.getProductList();
        }

        return ResponseEntity.status(HttpStatus.OK).header("Access-Control-Allow-Origin", "*").body(productDtoList);
    }

    @GetMapping(value = "/api/v1/products/{uuid}", produces = MediaType.PRODUCT)
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID uuid) {
        if (!productService.isProductExistByUuid(uuid)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).header("Access-Control-Allow-Origin", "*").body(productService.getProductDtoByUuid(uuid));
    }

    @GetMapping(value = "/api/v1/products/{uuid}/reviews", produces = MediaType.REVIEW_LIST)
    public ResponseEntity<List<ReviewDto>> getReviewList(@PathVariable UUID uuid) {
        final var status = ResponseEntity.status(HttpStatus.OK);
        return status.header("Access-Control-Allow-Origin","*").body(reviewService.getReviewListByProductUuid(uuid));
    }

    @PostMapping(value = "/api/v1/products/{uuid}/reviews", consumes = MediaType.REVIEW)
    public ResponseEntity<ReviewDto> addReview(@PathVariable UUID uuid, @RequestBody ReviewDto reviewDto) {
        final ReviewDto createdReview = reviewService.createReview(reviewDto);

        return ResponseEntity.status(HttpStatus.CREATED).header("Access-Control-Allow-Origin", "*").body(createdReview);
    }

    @GetMapping(value = "/api/v1/products/{uuidProduct}/reviews/{uuidReview}", produces = MediaType.REVIEW)
    public ResponseEntity<ReviewDto> getReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview) {
        return ResponseEntity.status(HttpStatus.OK).header("Access-Control-Allow-Origin", "*").body(reviewService.getByUuid(uuidReview));
    }

    @PutMapping(value = "/api/v1/products/{uuidProduct}/reviews/{uuidReview}", consumes = MediaType.REVIEW)
    public ResponseEntity<ReviewDto> updateReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview, @RequestBody ReviewDto reviewDto) {
        final var status = ResponseEntity.status(HttpStatus.OK);

        return status.header("Access-Control-Allow-Origin", "*").body(reviewService.updateByUuid(uuidReview, reviewDto));
    }

    @DeleteMapping(value = "/api/v1/products/{uuidProduct}/reviews/{uuidReview}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview) {
        reviewService.deleteByUuid(uuidReview);
        return ResponseEntity.noContent().header("Access-Control-Allow-Origin", "*").build();
    }

    @GetMapping(value = "/api/v1/products/{uuidProduct}/image", produces = "image/png")
    public ResponseEntity<String> getImage(@PathVariable UUID uuidProduct) {
        final var status = ResponseEntity.status(HttpStatus.OK);

        return status.header("Access-Control-Allow-Origin", "*").body(productService.getImageByUuidProduct(uuidProduct));
    }
}
