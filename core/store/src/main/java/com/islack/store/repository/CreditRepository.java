package com.islack.store.repository;

import com.islack.store.domain.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, String> {
    Credit findByUsername(String username);
}
