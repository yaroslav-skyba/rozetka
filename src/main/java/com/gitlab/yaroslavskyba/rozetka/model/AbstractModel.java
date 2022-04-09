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
    static final int MAX_DESCRIPTION_LENGTH = 1000;
    static final int MAX_COLUMN_VARCHAR_LENGTH = 100;

    @CreatedDate
    @Column(name = "creation_date")
    protected LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "last_modification_date")
    protected LocalDateTime lastModificationDate;

    
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    
    public void setCreationDate(LocalDateTime created) {
        this.creationDate = created;
    }

    
    public LocalDateTime getLastModificationDate() {
        return lastModificationDate;
    }

    
    public void setLastModificationDate(LocalDateTime updated) {
        this.lastModificationDate = updated;
    }
}
