package com.gitlab.yarunkan.dto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "o2n_review")
public class Review {
    @Id
    @Column(name = "uuid_review")
    @GeneratedValue
    private UUID uuidReview;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "uuid_product")
    private Product product;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Size(min = 1, max = 5)
    private Integer rating;

    @NotNull
    @Column(name = "published", nullable = false)
    private Timestamp published;

    public UUID getUuidReview() {
        return uuidReview;
    }

    public void setUuidReview(UUID uuidReview) {
        this.uuidReview = uuidReview;
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

    public Timestamp getPublished() {
        return published;
    }

    public void setPublished(Timestamp published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return  uuidReview.equals(review.uuidReview) && product.equals(review.product) && content.equals(review.content) &&
                Objects.equals(rating, review.rating) && published.equals(review.published);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidReview, product, content, rating, published);
    }

    @Override
    public String toString() {
        return  "Review{" +
                "uuidReview=" + uuidReview +
                ", product=" + product +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", published=" + published +
                '}';
    }
}
