package com.islack.photograph.domain.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Tag {
    @Id
    private String tag;
    @Column(nullable = false, updatable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP()")
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Column(nullable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP()")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
