package com.islack.store.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Credit {
    @Id
    private String username;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getCredits() {
        return transactions.stream().map(Transaction::getCredit).reduce(0L, Long::sum);
    }
}
