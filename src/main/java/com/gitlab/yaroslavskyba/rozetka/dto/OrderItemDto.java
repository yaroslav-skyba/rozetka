package com.gitlab.yaroslavskyba.rozetka.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderItemDto {
    private UUID uuid;
    private UUID uuidProduct;
    private UUID uuidUser;

    
    public OrderItemDto() { }

    
    public OrderItemDto(UUID uuid, UUID uuidProduct, UUID uuidUser) {
        this.uuid = uuid;
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

        return uuid.equals(that.uuid) && uuidProduct.equals(that.uuidProduct) && uuidUser.equals(that.uuidUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, uuidProduct, uuidUser);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
               "uuid=" + uuid +
               ", uuidProduct=" + uuidProduct +
               ", uuidUser=" + uuidUser +
               '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUuidProduct() {
        return uuidProduct;
    }

    public UUID getUuidUser() {
        return uuidUser;
    }
}
