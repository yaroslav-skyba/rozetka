package com.gitlab.yarunkan.dto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "o2n_order_item")
public class OrderItem extends AbstractDto {
    @Id
    @Column(name = "id_order_item")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrderItem;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_product")
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_order")
    private Order order;

    @NotNull
    @Min(0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Min(0)//I am not sure that this will work for float
    @Column(name = "price", nullable = false)
    private Float price;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuidOrderItem) {
        this.uuid = uuidOrderItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Integer getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(Integer idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return  idOrderItem.equals(orderItem.idOrderItem) && uuid.equals(orderItem.uuid) && product.equals(orderItem.product) &&
                order.equals(orderItem.order) && quantity.equals(orderItem.quantity) && price.equals(orderItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrderItem, uuid, product, order, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "idOrderItem=" + idOrderItem +
                ", uuid=" + uuid +
                ", product=" + product +
                ", order=" + order +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
