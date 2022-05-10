package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "o2n_jwt")
public class Jwt extends AbstractModel {
    @Id
    @Column(name = "id_jwt")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJwt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    @Column(nullable = false, unique = true)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Jwt that = (Jwt) o;

        return idJwt.equals(that.idJwt) && user.equals(that.user) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJwt, user, value);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
               "idRefreshToken=" + idJwt +
               ", user=" + user +
               ", productName='" + value + '\'' +
               '}';
    }

    
    public Long getIdJwt() {
        return idJwt;
    }

    
    public void setIdJwt(Long idRefreshToken) {
        this.idJwt = idRefreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String name) {
        this.value = name;
    }
}
