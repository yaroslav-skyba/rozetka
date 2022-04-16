package com.gitlab.yaroslavskyba.rozetka.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderItemDto {
    private UUID uuidProduct;
    private UUID uuidUser;

    public OrderItemDto() { }

    public OrderItemDto(UUID uuidProduct, UUID uuidUser) {
        this.uuidProduct = uuidProduct;
        this.uuidUser = uuidUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final OrderItemDto that = (OrderItemDto) o;

        return uuidProduct.equals(that.uuidProduct) && uuidUser.equals(that.uuidUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidProduct, uuidUser);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
               ", uuidProduct=" + uuidProduct +
               ", uuidUser=" + uuidUser +
               '}';
    }

    public UUID getUuidProduct() {
        return uuidProduct;
    }

    public UUID getUuidUser() {
        return uuidUser;
    }
}
