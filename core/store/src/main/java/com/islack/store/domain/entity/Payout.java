package com.islack.store.domain.entity;

import javax.persistence.Entity;

@Entity
public class Payout extends Transaction {
    private String accountEmail;

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    @Override
    public Long getCredit() {
        return - this.credit;
    }

}
