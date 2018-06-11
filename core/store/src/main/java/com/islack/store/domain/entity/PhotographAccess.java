package com.islack.store.domain.entity;

import javax.persistence.Entity;

@Entity
public class PhotographAccess extends Transaction {
    private Long credit;
    private Long photoId;

    @Override
    public Long getCredit() {
        return - this.credit;
    }

    @Override
    public String getDescription() {
        return "Purchase photograph [id:" + photoId + "]";
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }
}
