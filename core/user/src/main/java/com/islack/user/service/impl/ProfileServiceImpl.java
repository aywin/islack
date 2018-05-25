package com.islack.user.service.impl;

import com.islack.user.domain.entity.Profile;
import com.islack.user.repository.ProfileRepository;
import com.islack.user.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public Optional<Profile> getByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

}
