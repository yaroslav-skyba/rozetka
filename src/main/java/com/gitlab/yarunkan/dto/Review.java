package com.gitlab.yarunkan.dto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "o2n_review")
public class Review extends AbstractDto {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReview;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_product")
    private Product product;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Size(min = 1, max = 5)
    private Integer rating;

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

    public Integer getIdReview() {
        return idReview;
    }

    public void setIdReview(Integer idReview) {
        this.idReview = idReview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return  idReview.equals(review.idReview) && uuid.equals(review.uuid) && product.equals(review.product) &&
                content.equals(review.content) && Objects.equals(rating, review.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReview, uuid, product, content, rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", uuid=" + uuid +
                ", product=" + product +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                '}';
    }
}
