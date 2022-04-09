package com.gitlab.yaroslavskyba.rozetka.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class UserDto {
    private UUID uuid;
    private UUID uuidRole;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthday;

    
    public UserDto() {
    }

    public UserDto(UUID uuid, UUID uuidRole, String login, String password, String email, String firstName, String lastName,
                   LocalDate birthday) {
        this.uuid = uuid;
        this.uuidRole = uuidRole;
        this.login = login;
        this.password = password;
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
               && Objects.equals(password, userDto.password) && Objects.equals(email, userDto.email)
               && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName)
               && Objects.equals(birthday, userDto.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, uuidRole, login, password, email, firstName, lastName, birthday);
    }

    @Override
    public String toString() {
        return "UserDto{" +
               "uuid=" + uuid +
               ", uuidRole=" + uuidRole +
               ", login='" + login + '\'' +
               ", passwordUser='" + password + '\'' +
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getBirthday() {
        return birthday;
    }
}
