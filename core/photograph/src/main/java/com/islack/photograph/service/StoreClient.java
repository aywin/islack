package com.islack.photograph.service;

import com.islack.photograph.domain.dto.PurchasePhotographDto;
import com.islack.photograph.domain.dto.TransactionDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "store")
public interface StoreClient {
    @PostMapping("/transaction/test")
    ResponseEntity<String> test(@RequestBody Long id);
    @PostMapping("/transaction/photograph")
    ResponseEntity<TransactionDto> purchasePhotograph(@RequestBody PurchasePhotographDto purchasePhotographDto);
}
