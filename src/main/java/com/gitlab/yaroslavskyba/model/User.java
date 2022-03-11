package com.gitlab.yaroslavskyba.model;

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
import java.sql.Timestamp;
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
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<OrderItem> orderItemList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Review> reviewList = new ArrayList<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password_user", nullable = false)
    private String passwordUser;

    @NotNull
    @Size(min = 1, max = 255)
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private Timestamp birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final User user = (User) o;

        return idUser.equals(user.idUser) && uuid.equals(user.uuid) && reviewList.equals(user.reviewList) && role.equals(user.role)
               && login.equals(user.login) && passwordUser.equals(user.passwordUser) && email.equals(user.email)
               && firstName.equals(user.firstName) && lastName.equals(user.lastName) && birthday.equals(user.birthday)
               && orderItemList.equals(user.orderItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, uuid, reviewList, role, login, passwordUser, email, firstName, lastName, birthday, orderItemList);
    }

    @Override
    public String toString() {
        return "User{" +
               "idUser=" + idUser +
               ", uuid=" + uuid +
               ", reviewList=" + reviewList +
               ", role=" + role +
               ", login='" + login + '\'' +
               ", passwordUser='" + passwordUser + '\'' +
               ", email='" + email + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthday=" + birthday +
               ", orderList=" + orderItemList +
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

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String password) {
        this.passwordUser = password;
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

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @SuppressWarnings("unused")
    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
