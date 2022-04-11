package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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

    @NotBlank
    @Size(max = MAX_COLUMN_VARCHAR_LENGTH)
    @Column(nullable = false, length = MAX_COLUMN_VARCHAR_LENGTH)
    private String name;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal price;

    @DecimalMin("0")
    @DecimalMax("100")
    @Column(precision = 9, scale = 2)
    private BigDecimal discount;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    @Column(length = MAX_DESCRIPTION_LENGTH)
    private String description;

    @NotNull
    @Size(max = MAX_VARCHAR_LENGTH)
    @Column(nullable = false, length = MAX_VARCHAR_LENGTH)
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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
