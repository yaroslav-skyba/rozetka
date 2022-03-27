package com.gitlab.yaroslavskyba.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "obj_role")
public class Role extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<User> userList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Role role = (Role) o;

        return idRole.equals(role.idRole) && uuid.equals(role.uuid) && name.equals(role.name) && userList.equals(role.userList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, uuid, name, userList);
    }

    @Override
    public String toString() {
        return "Role{" +
               "idRole=" + idRole +
               ", uuid=" + uuid +
               ", productName='" + name + '\'' +
               ", userList=" + userList +
               '}';
    }

    @SuppressWarnings("unused")
    public Long getIdRole() {
        return idRole;
    }

    @SuppressWarnings("unused")
    public void setIdRole(Long id) {
        this.idRole = id;
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

    @SuppressWarnings("unused")
    public List<User> getUserList() {
        return userList;
    }
}
