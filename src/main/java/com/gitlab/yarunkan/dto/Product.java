package com.gitlab.yarunkan.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "obj_product")
public class Product extends AbstractDto {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<OrderItem> orderItemList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Review> reviewList = new ArrayList<>();

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "name_product", nullable = false)
    private String nameProduct;

    @NotNull
    @PositiveOrZero
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    @Column(name = "price", nullable = false)
    private Float price;

    @Min(0)
    @Max(100)
    private Integer discount;

    @Size(max = 1024)
    private String description;

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuidProduct) {
        this.uuid = uuidProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setProduct(this);
        orderItemList.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItem.setProduct(null);
        orderItemList.remove(orderItem);
    }

    public void addReview(Review review) {
        review.setProduct(this);
        reviewList.add(review);
    }

    public void removeReview(Review review) {
        review.setProduct(null);
        reviewList.remove(review);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return  idProduct.equals(product.idProduct) && uuid.equals(product.uuid) && nameProduct.equals(product.nameProduct) &&
                quantity.equals(product.quantity) && price.equals(product.price) && Objects.equals(discount, product.discount) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, uuid, nameProduct, quantity, price, discount, description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", uuid=" + uuid +
                ", nameProduct='" + nameProduct + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                '}';
    }
}
