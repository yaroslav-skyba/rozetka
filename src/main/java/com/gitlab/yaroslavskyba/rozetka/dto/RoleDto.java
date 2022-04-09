package com.gitlab.yaroslavskyba.rozetka.dto;

import java.util.Objects;
import java.util.UUID;

public class RoleDto {
    private UUID uuid;
    private String name;

    
    public RoleDto() {
    }

    public RoleDto(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RoleDto roleDto = (RoleDto) o;

        return uuid.equals(roleDto.uuid) && name.equals(roleDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name);
    }

    @Override
    public String toString() {
        return "RoleDto{" +
               "uuid=" + uuid +
               ", nameRole='" + name + '\'' +
               '}';
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
}
