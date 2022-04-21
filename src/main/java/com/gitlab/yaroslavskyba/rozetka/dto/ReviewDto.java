package com.gitlab.yaroslavskyba.rozetka.dto;

import java.util.Objects;
import java.util.UUID;

public class ReviewDto {
    private UUID uuid;
    private UUID userUuid;
    private String userName;
    private String content;
    private Integer rating;

    public ReviewDto() {
    }

    public ReviewDto(UUID uuid, UUID userUuid, String userName, String content, Integer rating) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.userName = userName;
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

        return Objects.equals(uuid, reviewDto.uuid) && Objects.equals(userUuid, reviewDto.userUuid)
               && Objects.equals(userName, reviewDto.userName) && Objects.equals(content, reviewDto.content)
               && Objects.equals(rating, reviewDto.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, userUuid, userName, content, rating);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
               "uuid=" + uuid +
               ", userUuid=" + userUuid +
               ", userName='" + userName + '\'' +
               ", content='" + content + '\'' +
               ", rating=" + rating +
               '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
