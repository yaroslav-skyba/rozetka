package com.gitlab.yarunkan.dto;

import java.util.Objects;
import java.util.UUID;

public class ProductDto {
    private UUID uuid;
    private String name;
    private Integer quantity;
    private Float price;
    private Integer discount;
    private String description;

    public ProductDto() {
    }

    public ProductDto(UUID uuid, String name, Integer quantity, Float price, Integer discount, String description) {
        this.uuid = uuid;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return  Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(price, that.price) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, quantity, price, discount, description);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                '}';
    }
}