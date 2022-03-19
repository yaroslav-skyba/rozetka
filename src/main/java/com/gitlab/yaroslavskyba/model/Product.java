package com.gitlab.yaroslavskyba.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "obj_product")
public class Product extends AbstractModel {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "name", nullable = false, length = 1024)
    private String name;

    @NotNull
    @Range
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "discount", nullable = false)
    private Float discount;

    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<OrderItem> orderItemList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Review> reviewList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Product product = (Product) o;

        return idProduct.equals(product.idProduct) && uuid.equals(product.uuid) && name.equals(product.name)
               && quantity.equals(product.quantity) && price.equals(product.price) && discount.equals(product.discount)
               && Objects.equals(description, product.description) && orderItemList.equals(product.orderItemList)
               && reviewList.equals(product.reviewList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, uuid, name, quantity, price, discount, description, orderItemList, reviewList);
    }

    @Override
    public String toString() {
        return "Product{" +
               "idProduct=" + idProduct +
               ", uuid=" + uuid +
               ", name='" + name + '\'' +
               ", quantity=" + quantity +
               ", price=" + price +
               ", discount=" + discount +
               ", description='" + description + '\'' +
               ", orderItemList=" + orderItemList +
               ", reviewList=" + reviewList +
               '}';
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    @SuppressWarnings("unused")
    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuidProduct) {
        this.uuid = uuidProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameProduct) {
        this.name = nameProduct;
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

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    @SuppressWarnings("unused")
    @AssertTrue
    public boolean isDiscountValid() {
        return discount >= 0 && discount <= 100;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unused")
    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    @SuppressWarnings("unused")
    public List<Review> getReviewList() {
        return reviewList;
    }
}
