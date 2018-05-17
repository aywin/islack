package com.islack.store.service;

import com.islack.store.domain.entity.MyMessage;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gateway")
@RibbonClient(name = "user")
public interface FeignService {
    @GetMapping("user/users/find-by-username/{username}")
    ResponseEntity<MyMessage> getUser(@PathVariable("username") String username);
}
