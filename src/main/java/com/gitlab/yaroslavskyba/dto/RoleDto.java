package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;
import java.util.UUID;

public class RoleDto {
    private UUID uuid;
    private String nameRole;

    public RoleDto() {
    }

    public RoleDto(UUID uuid, String nameRole) {
        this.uuid = uuid;
        this.nameRole = nameRole;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
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

        return uuid.equals(roleDto.uuid) && nameRole.equals(roleDto.nameRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nameRole);
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "uuid=" + uuid +
                ", nameRole='" + nameRole + '\'' +
                '}';
    }
}
