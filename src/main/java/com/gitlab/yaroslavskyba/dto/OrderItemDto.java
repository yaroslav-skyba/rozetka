package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderItemDto {
    private UUID uuid;
    private UUID uuidProduct;
    private UUID uuidOrder;

    @SuppressWarnings("unused")
    public OrderItemDto() { }

    public OrderItemDto(UUID uuid, UUID uuidProduct, UUID uuidOrder) {
        this.uuid = uuid;
        this.uuidProduct = uuidProduct;
        this.uuidOrder = uuidOrder;
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

        return uuid.equals(that.uuid) && uuidProduct.equals(that.uuidProduct) && uuidOrder.equals(that.uuidOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, uuidProduct, uuidOrder);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
               "uuid=" + uuid +
               ", productUuid=" + uuidProduct +
               ", orderUuid=" + uuidOrder +
               '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUuidProduct() {
        return uuidProduct;
    }

    public UUID getUuidOrder() {
        return uuidOrder;
    }
}
