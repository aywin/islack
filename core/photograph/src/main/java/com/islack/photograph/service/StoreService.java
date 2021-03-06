package com.islack.photograph.service;

import com.islack.photograph.domain.dto.PurchasePhotographDto;
import com.islack.photograph.domain.dto.TransactionDto;
import org.springframework.http.ResponseEntity;

public interface StoreService {
    ResponseEntity<TransactionDto> purchasePhotograph(String username, PurchasePhotographDto purchasePhotographDto);
    ResponseEntity<String> test(Long id);
}
