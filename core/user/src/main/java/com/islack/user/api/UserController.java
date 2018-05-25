package com.islack.user.api;

import com.islack.user.domain.entity.Profile;
import com.islack.user.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class UserController {

    @Autowired
    ProfileService profileService;

    @RequestMapping("mine")
    public ResponseEntity<Profile> findByEmail(Principal principal) {
        return profileService.getByUsername(principal.getName())
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
