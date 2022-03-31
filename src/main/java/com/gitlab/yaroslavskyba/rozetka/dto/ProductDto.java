package com.gitlab.yaroslavskyba.rozetka.dto;

import java.util.Objects;
import java.util.UUID;

public class ProductDto {
    private UUID uuid;
    private String name;
    private Integer quantity;
    private Float price;
    private Float discount;
    private String description;
    private String img;

    @SuppressWarnings("unused")
    public ProductDto() {
    }

    public ProductDto(UUID uuid, String name, Integer quantity, Float price, Float discount, String description, String img) {
        this.uuid = uuid;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ProductDto that = (ProductDto) o;

        return uuid.equals(that.uuid) && name.equals(that.name) && quantity.equals(that.quantity) && price.equals(that.price)
               && Objects.equals(discount, that.discount) && Objects.equals(description, that.description) && img.equals(that.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, quantity, price, discount, description, img);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
