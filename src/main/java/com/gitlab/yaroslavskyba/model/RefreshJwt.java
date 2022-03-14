package com.gitlab.yaroslavskyba.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "o2n_refresh_jwt")
public class RefreshJwt {
    @Id
    @Column(name = "id_refresh_token")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRefreshJwt;

    @NotNull
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @OneToOne
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

        final RefreshJwt that = (RefreshJwt) o;

        return idRefreshJwt.equals(that.idRefreshJwt) && uuid.equals(that.uuid) && Objects.equals(user, that.user)
               && value.equals(that.value) && expiryDate.equals(that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRefreshJwt, uuid, user, value, expiryDate);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
               "idRefreshToken=" + idRefreshJwt +
               ", uuid=" + uuid +
               ", user=" + user +
               ", name='" + value + '\'' +
               ", expiryDate=" + expiryDate +
               '}';
    }

    public Long getIdRefreshJwt() {
        return idRefreshJwt;
    }

    public void setIdRefreshJwt(Long idRefreshToken) {
        this.idRefreshJwt = idRefreshToken;
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
