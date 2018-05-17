package com.islack.user.service;


import com.islack.user.domain.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUsername(String username);
    Optional<User> getByEmail(String email);
}
