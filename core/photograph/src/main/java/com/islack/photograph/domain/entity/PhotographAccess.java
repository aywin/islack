package com.islack.photograph.domain.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class PhotographAccess {
    @Id
    private String username;
    @Column(nullable = false)
    private Long credit;
    @ManyToOne(fetch = FetchType.EAGER)
    private Photograph photograph;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @ColumnDefault(value = "GETDATE()")
    private Date createdDate;
    @Column(nullable = false)
    @ColumnDefault(value = "GETDATE()")
    @UpdateTimestamp
    private Date updatedDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public Photograph getPhotograph() {
        return photograph;
    }

    public void setPhotograph(Photograph photograph) {
        this.photograph = photograph;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
