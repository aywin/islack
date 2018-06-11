package com.islack.photograph.domain.dto;

import com.islack.photograph.domain.entity.Photograph;

public class PurchasePhotographDto {
    private Long photoId;
    private Long credit;

    public PurchasePhotographDto(Photograph p) {
        photoId = p.getId();
        credit = p.getCredit();
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }
}
