package com.islack.store.domain.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Purchase extends Transaction {
    @ManyToOne
    private Offer offer;

    private Long credit;

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    @Override
    public Long getCredit() {
        return credit;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
        this.credit = offer.getCredit();
    }

    public String getDescription() {
        return "Purchase offer: " + offer.getName();
    }
}
