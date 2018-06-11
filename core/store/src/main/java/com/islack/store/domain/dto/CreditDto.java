package com.islack.store.domain.dto;

import com.islack.store.domain.entity.Credit;
import com.islack.store.domain.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CreditDto {
    public Long credits;
    public List<TransactionDto> transactions = new ArrayList<>();

    public CreditDto(Credit credit) {
        credits = credit.getCredits();
        for(Transaction t: credit.getTransactions()) {
            transactions.add(new TransactionDto(t));
        }
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }
}
