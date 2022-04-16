package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Order order = (Order) o;

        return idOrder.equals(order.idOrder) && uuid.equals(order.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, uuid);
    }

    @Override
    public String toString() {
        return "Order{" +
               "idOrder=" + idOrder +
               ", uuid=" + uuid +
               '}';
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
