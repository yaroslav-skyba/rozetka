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
    @Column(name = "name_role", unique = true, nullable = false)
    private String nameRole;

    @OneToMany(mappedBy = "role", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<User> userList = new ArrayList<>();

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long id) {
        this.idRole = id;
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

    public void setNameRole(String name) {
        this.nameRole = name;
    }

    public void addUser(User user) {
        userList.add(user);
        user.setRole(this);
    }

    public void removeUser(User user) {
        userList.remove(user);
        user.setRole(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Role role = (Role) o;

        return Objects.equals(idRole, role.idRole) && Objects.equals(nameRole, role.nameRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, nameRole);
    }

    @Override
    public String toString() {
        return "Role{" +
               "id=" + idRole +
               ", name='" + nameRole + '\'' +
               '}';
    }
}
