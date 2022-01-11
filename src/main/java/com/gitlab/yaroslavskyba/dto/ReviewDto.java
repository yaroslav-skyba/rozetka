package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;
import java.util.UUID;

public class ReviewDto {
    private UUID uuid;
    private UUID uuidProduct;
    private String content;
    private Integer rating;

    public ReviewDto() {
    }

    public ReviewDto(UUID uuid, UUID uuidProduct, String content, Integer rating) {
        this.uuid = uuid;
        this.uuidProduct = uuidProduct;
        this.content = content;
        this.rating = rating;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUuidProduct() {
        return uuidProduct;
    }

    public String getContent() {
        return content;
    }

    public Integer getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return  Objects.equals(uuid, reviewDto.uuid) &&
                Objects.equals(uuidProduct, reviewDto.uuidProduct) &&
                Objects.equals(content, reviewDto.content) &&
                Objects.equals(rating, reviewDto.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, uuidProduct, content, rating);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "uuid=" + uuid +
                ", product=" + uuidProduct +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                '}';
    }
}
