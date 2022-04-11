package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @NotBlank
    @Size(max = MAX_COLUMN_VARCHAR_LENGTH)
    @Column(unique = true, nullable = false, length = MAX_COLUMN_VARCHAR_LENGTH)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }
}
