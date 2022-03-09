package com.gitlab.yaroslavskyba.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "obj_order")
public class Order extends AbstractModel {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<OrderItem> orderItemList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Order order = (Order) o;

        return idOrder.equals(order.idOrder) && uuid.equals(order.uuid) && Objects.equals(description, order.description)
               && orderItemList.equals(order.orderItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, uuid, description, orderItemList);
    }

    @Override
    public String toString() {
        return "Order{" +
               "idOrder=" + idOrder +
               ", uuid=" + uuid +
               ", description='" + description + '\'' +
               ", orderItemList=" + orderItemList +
               '}';
    }

    @SuppressWarnings("unused")
    public Integer getIdOrder() {
        return idOrder;
    }

    @SuppressWarnings("unused")
    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuidOrder) {
        this.uuid = uuidOrder;
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
}
