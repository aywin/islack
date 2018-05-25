package com.islack.oauth.service;

import com.islack.oauth.model.Account;
import com.islack.oauth.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userDetailsServiceImpl")
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        Optional<Account> account = input.contains("@") ? accountRepository.findByEmail(input) : accountRepository.findByUsername(input);

        account.orElseThrow(() -> new UsernameNotFoundException("Account <" + input + "> could not be found"));

        detailsChecker.check(account.get());

        logger.warn(input);
        logger.warn(account.get().toString());

        return account.get();
    }
}