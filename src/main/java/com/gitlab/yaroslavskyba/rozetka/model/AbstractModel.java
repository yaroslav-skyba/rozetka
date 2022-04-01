package com.gitlab.yaroslavskyba.rozetka.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModel {
    static final int MAX_VARCHAR_LENGTH = 10485760;
    static final int MAX_COLUMN_LENGTH = 1024;

    @CreatedDate
    @Column(name = "creation_date")
    protected LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "last_modification_date")
    protected LocalDateTime lastModificationDate;

    @SuppressWarnings("unused")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @SuppressWarnings("unused")
    public void setCreationDate(LocalDateTime created) {
        this.creationDate = created;
    }

    @SuppressWarnings("unused")
    public LocalDateTime getLastModificationDate() {
        return lastModificationDate;
    }

    @SuppressWarnings("unused")
    public void setLastModificationDate(LocalDateTime updated) {
        this.lastModificationDate = updated;
    }
}
