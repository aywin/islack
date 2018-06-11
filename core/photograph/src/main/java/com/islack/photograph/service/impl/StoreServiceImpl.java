package com.islack.photograph.service.impl;

import com.islack.photograph.domain.dto.PurchasePhotographDto;
import com.islack.photograph.domain.dto.TransactionDto;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.PhotographAccess;
import com.islack.photograph.repository.PhotographAccessRepository;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PhotographAccessRepository photographAccessRepository;

    @Autowired
    private PhotographRepository photographRepository;

    @Override
    public ResponseEntity<TransactionDto> purchasePhotograph(String username, PurchasePhotographDto purchasePhotographDto, String bearer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearer);
        HttpEntity<PurchasePhotographDto> req = new HttpEntity<>(purchasePhotographDto, headers);
        ResponseEntity<TransactionDto> r = restTemplate.postForEntity("http://store/transaction/photograph", req, TransactionDto.class);

        if(r.getStatusCode().is2xxSuccessful()) {
            Photograph p = photographRepository.findOne(purchasePhotographDto.getPhotoId());
            PhotographAccess access = new PhotographAccess();
            access.setPhotograph(p);
            access.setUsername(username);
            access.setCredit(p.getCredit());
            photographAccessRepository.save(access);
            return r;
        }

        return new ResponseEntity<>((TransactionDto) null, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> test(Long id, String bearer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearer);
        HttpEntity<Long> req = new HttpEntity<>(id, headers);
        return restTemplate.postForEntity("http://store/transaction/test", req, String.class);
    }
}
