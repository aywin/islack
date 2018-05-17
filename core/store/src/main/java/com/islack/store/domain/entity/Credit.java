package com.islack.store.domain.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Credit {
    @Id
    private Long userId;
    private Long credit;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PhotographAccess> photographAccessList;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;


    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<PhotographAccess> getPhotographAccessList() {
        return photographAccessList;
    }

    public void setPhotographAccessList(List<PhotographAccess> photographAccessList) {
        this.photographAccessList = photographAccessList;
    }
}
