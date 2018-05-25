package com.islack.user.service;


import com.islack.user.domain.entity.Profile;

import java.util.Optional;

public interface ProfileService {
    Optional<Profile> getByUsername(String username);
    Optional<Profile> getByEmail(String email);
}
