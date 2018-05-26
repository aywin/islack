package com.islack.store.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Credit {
    @Id
    private String username;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Transaction> transactions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getCredits() {
        return transactions.stream().map(Transaction::getCredit).reduce(0L, Long::sum);
    }
}
