package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderDto {
    private UUID uuid;
    private UUID uuidUser;
    private String description;

    @SuppressWarnings("unused")
    public OrderDto() {
    }

    public OrderDto(UUID uuid, UUID uuidUser, String description) {
        this.uuid = uuid;
        this.uuidUser = uuidUser;
        this.description = description;
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

        return Objects.equals(uuid, orderDto.uuid) && Objects.equals(uuidUser, orderDto.uuidUser) &&
               Objects.equals(description, orderDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, uuidUser, description);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
               "uuid=" + uuid +
               ", orderUuid=" + uuidUser +
               ", description='" + description + '\'' +
               '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUuidUser() {
        return uuidUser;
    }

    public String getDescription() {
        return description;
    }
}
