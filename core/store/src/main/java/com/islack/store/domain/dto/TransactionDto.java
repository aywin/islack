package com.islack.store.domain.dto;

import com.islack.store.domain.entity.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDto {
    private Long id;
    private String date;
    private Long credit;
    private String description;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.date = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(transaction.getCreatedDate());
        this.credit = transaction.getCredit();
        this.description = transaction.getDescription();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
