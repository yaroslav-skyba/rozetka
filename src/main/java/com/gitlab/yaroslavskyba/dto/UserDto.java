package com.gitlab.yaroslavskyba.dto;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class UserDto {
    private UUID uuid;
    private UUID roleUuid;
    private String login;
    private String passwordUser;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp birthday;

    public UserDto() {
    }

    public UserDto(UUID uuid,
                   UUID roleUuid,
                   String login,
                   String passwordUser,
                   String email,
                   String firstName,
                   String lastName,
                   Timestamp birthday) {
        this.uuid = uuid;
        this.roleUuid = roleUuid;
        this.login = login;
        this.passwordUser = passwordUser;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getRoleUuid() {
        return roleUuid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UserDto userDto = (UserDto) o;

        return uuid.equals(userDto.uuid)
                && roleUuid.equals(userDto.roleUuid)
                && login.equals(userDto.login)
                && passwordUser.equals(userDto.passwordUser)
                && email.equals(userDto.email)
                && firstName.equals(userDto.firstName)
                && lastName.equals(userDto.lastName)
                && birthday.equals(userDto.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, roleUuid, login, passwordUser, email, firstName, lastName, birthday);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "uuid=" + uuid +
                ", roleUuid=" + roleUuid +
                ", login='" + login + '\'' +
                ", passwordUser='" + passwordUser + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
