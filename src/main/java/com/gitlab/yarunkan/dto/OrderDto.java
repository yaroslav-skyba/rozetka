package com.gitlab.yarunkan.dto;

import java.util.Objects;
import java.util.UUID;

public class OrderDto {
    private UUID uuid;
    private String description;

    public OrderDto() {
    }

    public OrderDto(UUID uuid, String description) {
        this.uuid = uuid;
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return  Objects.equals(uuid, orderDto.uuid) &&
                Objects.equals(description, orderDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, description);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "uuid=" + uuid +
                ", description='" + description + '\'' +
                '}';
    }
}
