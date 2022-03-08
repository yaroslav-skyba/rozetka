package com.gitlab.yaroslavskyba.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModel {
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    protected Timestamp created;

    @Column(name = "updated", nullable = false)
    @LastModifiedDate
    protected Timestamp updated;

    @SuppressWarnings("unused")
    public Timestamp getCreated() {
        return created;
    }

    @SuppressWarnings("unused")
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @SuppressWarnings("unused")
    public Timestamp getUpdated() {
        return updated;
    }

    @SuppressWarnings("unused")
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
