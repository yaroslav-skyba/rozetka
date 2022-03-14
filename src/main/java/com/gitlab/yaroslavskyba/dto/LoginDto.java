package com.gitlab.yaroslavskyba.dto;

import java.util.Objects;

public class LoginDto {
    private String username;
    private String password;

    @SuppressWarnings("unused")
    public LoginDto() {
    }

    @SuppressWarnings("unused")
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final LoginDto that = (LoginDto) o;

        return username.equals(that.username) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
               "login='" + username + '\'' +
               ", password='" + password + '\'' +
               '}';
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
