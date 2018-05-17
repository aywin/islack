package com.islack.store.domain.entity;

import javax.persistence.Entity;

@Entity
public class Payout extends Transaction {
    private String accountEmail;
    private Double amount;

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
