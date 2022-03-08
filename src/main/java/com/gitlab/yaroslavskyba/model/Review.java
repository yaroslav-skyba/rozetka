package com.gitlab.yaroslavskyba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "o2n_review")
public class Review extends AbstractModel {
    @Id
    @Column(name = "id_review")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReview;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @Size(max = 1024)
    @NotEmpty
    @Column(name = "content", nullable = false, length = 1024)
    private String content;

    @Min(1)
    @Max(5)
    private Integer rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Review review = (Review) o;

        return idReview.equals(review.idReview) && uuid.equals(review.uuid) && product.equals(review.product)
               && user.equals(review.user) && content.equals(review.content) && Objects.equals(rating, review.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReview, uuid, product, user, content, rating);
    }

    @Override
    public String toString() {
        return "Review{" +
               "idReview=" + idReview +
               ", uuid=" + uuid +
               ", product=" + product +
               ", user=" + user +
               ", content='" + content + '\'' +
               ", rating=" + rating +
               '}';
    }

    @SuppressWarnings("unused")
    public Integer getIdReview() {
        return idReview;
    }

    @SuppressWarnings("unused")
    public void setIdReview(Integer idReview) {
        this.idReview = idReview;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuidReview) {
        this.uuid = uuidReview;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
