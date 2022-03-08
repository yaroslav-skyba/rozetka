package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderDto {
    private UUID uuid;
    private UUID userUuid;
    private String description;

    @SuppressWarnings("unused")
    public OrderDto() {
    }

    public OrderDto(UUID uuid, UUID userUuid, String description) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final OrderDto orderDto = (OrderDto) o;

        return Objects.equals(uuid, orderDto.uuid) && Objects.equals(userUuid, orderDto.userUuid) &&
               Objects.equals(description, orderDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, userUuid, description);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
               "uuid=" + uuid +
               ", orderUuid=" + userUuid +
               ", description='" + description + '\'' +
               '}';
    }
}
