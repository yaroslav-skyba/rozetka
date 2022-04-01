package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
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
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(nullable = false, length = 1024)
    private String name;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Float price;

    private Float discount;

    @Size(max = 1024)
    @Column(length = 1024)
    private String description;

    @NotNull
    @Column(nullable = false)
    private String img;

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
               && quantity.equals(product.quantity) && price.equals(product.price) && Objects.equals(discount, product.discount)
               && Objects.equals(description, product.description) && img.equals(product.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, uuid, name, quantity, price, discount, description, img);
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
               ", img='" + img + '\'' +
               '}';
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
