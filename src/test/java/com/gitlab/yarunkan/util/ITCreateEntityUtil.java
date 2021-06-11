package com.gitlab.yarunkan.util;

import com.gitlab.yarunkan.dto.Order;
import com.gitlab.yarunkan.dto.OrderItem;
import com.gitlab.yarunkan.dto.Product;
import com.gitlab.yarunkan.dto.Review;
import net.bytebuddy.utility.RandomString;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ITCreateEntityUtil {

    private static final int MAX_STRING_LENGTH = 1024;

    public static Order createOrder() {
        final var order = new Order();
        order.setDescription(RandomString.make(MAX_STRING_LENGTH));
        order.setUuid(UUID.randomUUID());

        return order;
    }

    public static OrderItem createOrderItem(Order order, Product product) {
        final var random = new Random();
        final int max = 101;

        final var orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setPrice(random.nextFloat() * max);
        orderItem.setProduct(product);
        orderItem.setQuantity(random.nextInt(max));
        orderItem.setUuid(UUID.randomUUID());

        return orderItem;
    }

    public static Product createProduct() {
        final Random random = new Random();
        final int maxNumber = 101;

        final Product product = new Product();
        product.setDescription(RandomString.make(MAX_STRING_LENGTH));
        product.setDiscount(random.nextInt(maxNumber));
        product.setNameProduct(RandomString.make(MAX_STRING_LENGTH));
        product.setPrice(random.nextFloat() * maxNumber * product.getDiscount() / 100);
        product.setQuantity(random.nextInt(maxNumber));
        product.setUuid(UUID.randomUUID());

        return product;
    }

    public static Review createReview(Product product) {

        final Review review = new Review();
        review.setContent(RandomString.make(MAX_STRING_LENGTH));
        review.setProduct(product);
        review.setRating(ThreadLocalRandom.current().nextInt(1, 6));
        review.setUuid(UUID.randomUUID());

        return review;
    }
}
