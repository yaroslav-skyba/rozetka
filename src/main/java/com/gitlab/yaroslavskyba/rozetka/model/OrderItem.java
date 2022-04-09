package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "o2n_order_item")
public class OrderItem extends AbstractModel {
    @Id
    @Column(name = "id_order_item")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrderItem;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final OrderItem orderItem = (OrderItem) o;

        return idOrderItem.equals(orderItem.idOrderItem) && uuid.equals(orderItem.uuid) && product.equals(orderItem.product)
               && user.equals(orderItem.user) && order.equals(orderItem.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrderItem, uuid, product, user, order);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
               "idOrderItem=" + idOrderItem +
               ", uuid=" + uuid +
               ", product=" + product +
               ", user=" + user +
               ", order=" + order +
               '}';
    }

    
    public Integer getIdOrderItem() {
        return idOrderItem;
    }

    
    public void setIdOrderItem(Integer idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
