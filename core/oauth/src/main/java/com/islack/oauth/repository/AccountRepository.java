package com.islack.oauth.repository;

import com.islack.oauth.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(final String username);
    Optional<Account> findByEmail(final String email);

}
