package com.islack.store.domain.entity;

import javax.persistence.Entity;

@Entity
public class Payout extends Transaction {
    private Long amount;
    private String accountEmail;

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    @Override
    public Long getCredit() {
        return - this.amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return "Payout amout: " + getCredit();
    }
}
