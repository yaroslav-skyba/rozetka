package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "o2n_user")
public class User extends AbstractModel {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotNull
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(unique = true, nullable = false)
    private String login;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Size(min = 1, max = 1024)
    @Email(message = "text@text")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate birthday;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Review> reviewList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final User user = (User) o;

        return idUser.equals(user.idUser) && uuid.equals(user.uuid) && role.equals(user.role) && login.equals(user.login)
               && password.equals(user.password) && email.equals(user.email) && firstName.equals(user.firstName)
               && lastName.equals(user.lastName) && birthday.equals(user.birthday) && reviewList.equals(user.reviewList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, uuid, role, login, password, email, firstName, lastName, birthday, reviewList);
    }

    @Override
    public String toString() {
        return "User{" +
               "idUser=" + idUser +
               ", uuid=" + uuid +
               ", role=" + role +
               ", login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", email='" + email + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthday=" + birthday +
               ", reviewList=" + reviewList +
               '}';
    }

    @SuppressWarnings("unused")
    public Long getIdUser() {
        return idUser;
    }

    @SuppressWarnings("unused")
    public void setIdUser(Long id) {
        this.idUser = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
