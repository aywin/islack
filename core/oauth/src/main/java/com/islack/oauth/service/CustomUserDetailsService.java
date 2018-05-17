package com.islack.oauth.service;

import com.islack.oauth.model.User;
import com.islack.oauth.repository.UserRepository;
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

    private final UserRepository userRepository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) {
        Logger logger = LoggerFactory.getLogger(this.getClass());


        Optional<User> user = input.contains("@") ? userRepository.findByEmail(input) : userRepository.findByUsername(input);

        user.orElseThrow(() -> new UsernameNotFoundException("User <" + input + "> could not be found"));

        detailsChecker.check(user.get());

        logger.warn(input);
        logger.warn(user.get().toString());

        return user.get();
    }
}