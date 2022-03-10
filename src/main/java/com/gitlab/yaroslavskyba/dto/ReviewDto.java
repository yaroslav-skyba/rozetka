package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;
import java.util.UUID;

public class ReviewDto {
    private UUID uuid;
    private UUID uuidProduct;
    private UUID uuidUser;
    private String content;
    private Integer rating;

    @SuppressWarnings("unused")
    public ReviewDto() {
    }

    public ReviewDto(UUID uuid, UUID uuidProduct, UUID uuidUser, String content, Integer rating) {
        this.uuid = uuid;
        this.uuidProduct = uuidProduct;
        this.uuidUser = uuidUser;
        this.content = content;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ReviewDto reviewDto = (ReviewDto) o;

        return Objects.equals(uuid, reviewDto.uuid) && Objects.equals(uuidProduct, reviewDto.uuidProduct)
               && Objects.equals(uuidUser, reviewDto.uuidUser) && Objects.equals(content, reviewDto.content)
               && Objects.equals(rating, reviewDto.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, uuidProduct, uuidUser, content, rating);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
               "uuid=" + uuid +
               ", uuidProduct=" + uuidProduct +
               ", uuidUser=" + uuidUser +
               ", content='" + content + '\'' +
               ", rating=" + rating +
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

    public String getContent() {
        return content;
    }

    public Integer getRating() {
        return rating;
    }
}
