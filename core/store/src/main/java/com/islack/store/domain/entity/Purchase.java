package com.islack.store.domain.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Purchase extends Transaction {
    @ManyToOne
    private Offer offer;

    @Override
    public Long getCredit() {
        return offer.getCredit();
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
