package com.gitlab.yaroslavskyba.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

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

@Entity
@Table(name = "o2n_jwt")
public class Jwt {
    @Id
    @Column(name = "id_jwt")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJwt;

    @NotNull
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    @Column(nullable = false, unique = true)
    private String value;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Jwt that = (Jwt) o;

        return idJwt.equals(that.idJwt) && uuid.equals(that.uuid) && user.equals(that.user) && Objects.equals(user, that.user)
               && expiryDate.equals(that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJwt, uuid, user, value, expiryDate);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
               "idRefreshToken=" + idJwt +
               ", uuid=" + uuid +
               ", user=" + user +
               ", productName='" + value + '\'' +
               ", expiryDate=" + expiryDate +
               '}';
    }

    @SuppressWarnings("unused")
    public Long getIdJwt() {
        return idJwt;
    }

    @SuppressWarnings("unused")
    public void setIdJwt(Long idRefreshToken) {
        this.idJwt = idRefreshToken;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
