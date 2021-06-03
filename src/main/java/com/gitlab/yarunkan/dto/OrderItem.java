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
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "o2n_order_item")
public class OrderItem {
    @Id
    @Column(name = "uuid_order_item")
    @GeneratedValue
    private UUID uuidOrderItem;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "uuid_product")
    private Product product;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "uuid_order")
    private Order order;

    @NotNull
    @Size
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Size
    @Column(name = "price", nullable = false)
    private Float price;

    public UUID getUuidOrderItem() {
        return uuidOrderItem;
    }

    public void setUuidOrderItem(UUID uuidOrderItem) {
        this.uuidOrderItem = uuidOrderItem;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return  uuidOrderItem.equals(orderItem.uuidOrderItem) && product.equals(orderItem.product) && order.equals(orderItem.order) &&
                quantity.equals(orderItem.quantity) && price.equals(orderItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidOrderItem, product, order, quantity, price);
    }

    @Override
    public String toString() {
        return  "OrderItem{" +
                "uuidOrderItem=" + uuidOrderItem +
                ", product=" + product +
                ", order=" + order +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
