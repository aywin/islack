package com.islack.store.api;

import com.islack.store.domain.entity.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("store")
public class StoreController {


    @GetMapping("test")
    public ResponseEntity<MyMessage> test()
    {
        return new ResponseEntity<>(new MyMessage(), HttpStatus.OK);
    }

}
