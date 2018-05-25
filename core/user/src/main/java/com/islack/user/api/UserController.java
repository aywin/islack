package com.islack.user.api;

import com.islack.user.domain.entity.Profile;
import com.islack.user.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    ProfileService profileService;

    @RequestMapping("mine/{username}")
    public ResponseEntity<Profile> findByEmail(@PathVariable("username") String username) {
        return profileService.getByUsername(username)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
