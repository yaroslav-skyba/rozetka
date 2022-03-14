package com.gitlab.yaroslavskyba.model;

import java.time.Instant;

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
@Table(name = "o2n_refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "id_refresh_token")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRefreshToken;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    @Column(nullable = false, unique = true)
    private String token;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    public long getIdRefreshToken() {
        return idRefreshToken;
    }

    public void setIdRefreshToken(long id) {
        this.idRefreshToken = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
