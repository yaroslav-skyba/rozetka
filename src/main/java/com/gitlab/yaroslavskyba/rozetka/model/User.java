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
import javax.validation.constraints.NotBlank;
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

    @NotNull(message = "A UUID should be present")
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @NotNull(message = "A role should be present")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @NotBlank(message = "Login should be present and contains at least one non-whitespace character")
    @Size(max = MAX_COLUMN_VARCHAR_LENGTH, message = "Login should be less or equal to " + MAX_COLUMN_VARCHAR_LENGTH + " symbols")
    @Column(unique = true, nullable = false, length = MAX_COLUMN_VARCHAR_LENGTH)
    private String login;

    @NotBlank(message = "A password should be present and contains at least one non-whitespace character")
    @Size(max = MAX_COLUMN_VARCHAR_LENGTH, message = "A password should be less or equal to " + MAX_COLUMN_VARCHAR_LENGTH + " symbols")
    @Column(nullable = false, length = MAX_COLUMN_VARCHAR_LENGTH)
    private String password;

    @NotBlank(message = "An email should be present and contains at least one non-whitespace character before @ symbol and one after")
    @Size(max = MAX_COLUMN_VARCHAR_LENGTH, message = "An email should be less or equal to " + MAX_COLUMN_VARCHAR_LENGTH + " symbols")
    @Email(message = "An email should be in such the format as text@text")
    @Column(unique = true, nullable = false, length = MAX_COLUMN_VARCHAR_LENGTH)
    private String email;

    @NotBlank(message = "A first name should be present and contains at least one non-whitespace character")
    @Size(max = MAX_COLUMN_VARCHAR_LENGTH, message = "A first name should be less or equal to " + MAX_COLUMN_VARCHAR_LENGTH + " symbols")
    @Column(name = "first_name", nullable = false, length = MAX_COLUMN_VARCHAR_LENGTH)
    private String firstName;

    @NotBlank(message = "A last name should be present and contains at least one non-whitespace character")
    @Size(max = MAX_COLUMN_VARCHAR_LENGTH, message = "A last name should be less or equal to " + MAX_COLUMN_VARCHAR_LENGTH + " symbols")
    @Column(name = "last_name", nullable = false, length = MAX_COLUMN_VARCHAR_LENGTH)
    private String lastName;

    @NotNull(message = "A birthday should be present")
    @Column(nullable = false)
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

    public Long getIdUser() {
        return idUser;
    }

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
