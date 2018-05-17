package com.islack.store.domain.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Purchase extends Transaction {
    @ManyToOne
    private Offer offer;
}
