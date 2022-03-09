package com.gitlab.yaroslavskyba.dto;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class UserDto {
    private UUID uuid;
    private UUID uuidRole;
    private String login;
    private String passwordUser;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp birthday;

    @SuppressWarnings("unused")
    public UserDto() {
    }

    public UserDto(UUID uuid, UUID uuidRole, String login, String passwordUser, String email, String firstName, String lastName,
                   Timestamp birthday) {
        this.uuid = uuid;
        this.uuidRole = uuidRole;
        this.login = login;
        this.passwordUser = passwordUser;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UserDto userDto = (UserDto) o;

        return Objects.equals(uuid, userDto.uuid) && Objects.equals(uuidRole, userDto.uuidRole) && Objects.equals(login, userDto.login)
               && Objects.equals(passwordUser, userDto.passwordUser) && Objects.equals(email, userDto.email)
               && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName)
               && Objects.equals(birthday, userDto.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, uuidRole, login, passwordUser, email, firstName, lastName, birthday);
    }

    @Override
    public String toString() {
        return "UserDto{" +
               "uuid=" + uuid +
               ", uuidRole=" + uuidRole +
               ", login='" + login + '\'' +
               ", passwordUser='" + passwordUser + '\'' +
               ", email='" + email + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthday=" + birthday +
               '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUuidRole() {
        return uuidRole;
    }

    public void setUuidRole(UUID uuidRole) {
        this.uuidRole = uuidRole;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Timestamp getBirthday() {
        return birthday;
    }
}
