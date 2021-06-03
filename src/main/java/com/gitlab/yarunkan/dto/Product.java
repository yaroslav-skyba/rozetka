package com.gitlab.yarunkan.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "obj_product")
public class Product {
    @Id
    @Column(name = "uuid_product")
    @GeneratedValue
    private UUID uuidProduct;

    @NotNull
    @Column(name = "name_product", nullable = false)
    private String nameProduct;

    @NotNull
    @Size
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Size
    @Column(name = "price", nullable = false)
    private Float price;

    @Size(max = 100)
    private Integer discount;

    private String description;

    public UUID getUuidProduct() {
        return uuidProduct;
    }

    public void setUuidProduct(UUID uuidProduct) {
        this.uuidProduct = uuidProduct;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return  uuidProduct.equals(product.uuidProduct) && nameProduct.equals(product.nameProduct) && quantity.equals(product.quantity) &&
                price.equals(product.price) && Objects.equals(discount, product.discount) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidProduct, nameProduct, quantity, price, discount, description);
    }

    @Override
    public String toString() {
        return  "Product{" +
                "uuidProduct=" + uuidProduct +
                ", nameProduct='" + nameProduct + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                '}';
    }
}
