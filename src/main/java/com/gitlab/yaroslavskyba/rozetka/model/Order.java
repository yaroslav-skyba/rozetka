package com.gitlab.yaroslavskyba.rozetka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(max = MAX_COLUMN_LENGTH)
    @Column(name = "description", length = MAX_COLUMN_LENGTH)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Order order = (Order) o;

        return idOrder.equals(order.idOrder) && uuid.equals(order.uuid) && Objects.equals(description, order.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, uuid, description);
    }

    @Override
    public String toString() {
        return "Order{" +
               "idOrder=" + idOrder +
               ", uuid=" + uuid +
               ", productDescription='" + description + '\'' +
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

    @SuppressWarnings("unused")
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}