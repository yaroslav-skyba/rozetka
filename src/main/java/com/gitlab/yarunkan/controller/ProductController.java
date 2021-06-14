package com.gitlab.yarunkan.controller;

import com.gitlab.yarunkan.controller.util.MediaType;
import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.dto.Review;
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
import org.springframework.web.bind.annotation.RestController;
import java.awt.image.BufferedImage;
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

    @GetMapping(value = "/products", produces = MediaType.PRODUCT_LIST)
    public ResponseEntity<List<Product>> getProductList() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductList());
    }

    @GetMapping(value = "/products/{uuid}", produces = MediaType.PRODUCT)
    public ResponseEntity<Product> getProduct(@PathVariable UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByUuid(uuid));
    }

    @GetMapping(value = "/products/{uuid}/reviews", produces = MediaType.REVIEW_LIST)
    public ResponseEntity<List<Review>> getReviewList(@PathVariable UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewList());
    }

    @PostMapping(value = "/products/{uuid}/reviews", consumes = MediaType.REVIEW)
    public ResponseEntity<Review> addReview(@PathVariable UUID uuid, @RequestBody Review review) {
        final Review createdReview = reviewService.createReview(review.getProduct(), review.getContent(), review.getRating());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping(value = "/products/{uuidProduct}/reviews/{uuidReview}", produces = MediaType.REVIEW)
    public ResponseEntity<Review> getReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getByUuid(uuidReview));
    }

    @PutMapping(value = "/products/{uuidProduct}/reviews/{uuidReview}", consumes = MediaType.REVIEW)
    public ResponseEntity<Review> updateReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview, @RequestBody Review review) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateByUuid(uuidReview, review));
    }

    @DeleteMapping(value = "/products/{uuidProduct}/reviews/{uuidReview}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID uuidProduct, @PathVariable UUID uuidReview) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/products/{uuidProduct}/image", produces = "image/png")
    public ResponseEntity<BufferedImage> getImage(@PathVariable UUID uuidProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getImageByUuidProduct(uuidProduct));
    }
}
