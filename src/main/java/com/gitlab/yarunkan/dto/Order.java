package com.gitlab.yarunkan.dto;

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
public class Order extends AbstractDto {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    private String description;

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

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
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
                ", uuidOrder=" + uuid +
                ", description='" + description + '\'' +
                '}';
    }
}
