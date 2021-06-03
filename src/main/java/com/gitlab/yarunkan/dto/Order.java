package com.gitlab.yarunkan.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "obj_order")
public class Order {
    @Id
    @Column(name = "uuid_order")
    @GeneratedValue
    private UUID uuidOrder;

    @NotNull
    @Column(name = "created", nullable = false)
    private Timestamp created;

    private String description;

    public UUID getUuidOrder() {
        return uuidOrder;
    }

    public void setUuidOrder(UUID uuidOrder) {
        this.uuidOrder = uuidOrder;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
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
        Order order = (Order) o;
        return uuidOrder.equals(order.uuidOrder) && created.equals(order.created) && Objects.equals(description, order.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidOrder, created, description);
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuidOrder=" + uuidOrder +
                ", created=" + created +
                ", description='" + description + '\'' +
                '}';
    }
}
