package com.gitlab.yarunkan.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderItemDto {
    private UUID uuid;
    private UUID productUuid;
    private UUID orderUuid;
    private Float price;

    public OrderItemDto() { }

    public OrderItemDto(UUID uuid, UUID productUuid, UUID orderUuid, Float price) {
        this.uuid = uuid;
        this.productUuid = productUuid;
        this.orderUuid = orderUuid;
        this.price = price;
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

    public Float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return  Objects.equals(uuid, that.uuid) &&
                Objects.equals(productUuid, that.productUuid) &&
                Objects.equals(orderUuid, that.orderUuid) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, productUuid, orderUuid, price);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "uuid=" + uuid +
                ", product=" + productUuid +
                ", order=" + orderUuid +
                ", price=" + price +
                '}';
    }
}
