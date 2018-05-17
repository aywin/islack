package com.islack.user.api;

import com.islack.user.configuration.ClientConfiguration;
import com.islack.user.domain.entity.MyMessage;
import com.islack.user.domain.entity.User;
import com.islack.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private ClientConfiguration cf;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("find-by-username/{username}")
    public ResponseEntity<MyMessage> findByUsername(@PathVariable("username") String username) {
        MyMessage message = new MyMessage();
        message.setYasmessage("hello " + username);
        return new ResponseEntity<>(message, HttpStatus.OK);
   /*     return userService.getByUsername(username)
                .map(user1 -> new ResponseEntity<>(user1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
   */}

    @RequestMapping("find-by-email")
    public ResponseEntity<User> findByEmail(@RequestParam("email") String email) {
        return userService.getByEmail(email)
                .map(user1 -> new ResponseEntity<>(user1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>(cf.showProperties(), HttpStatus.OK);
    }
}
