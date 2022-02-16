package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderItemDto {
    private UUID uuid;
    private UUID productUuid;
    private UUID orderUuid;

    public OrderItemDto() { }

    public OrderItemDto(UUID uuid, UUID productUuid, UUID orderUuid) {
        this.uuid = uuid;
        this.productUuid = productUuid;
        this.orderUuid = orderUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getProductUuid() {
        return productUuid;
    }

    public UUID getOrderUuid() {
        return orderUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(productUuid, that.productUuid) &&
               Objects.equals(orderUuid, that.orderUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, productUuid, orderUuid);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "uuid=" + uuid +
                ", product=" + productUuid +
                ", order=" + orderUuid +
                '}';
    }
}
