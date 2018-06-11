package com.islack.photograph.service.impl;

import com.islack.photograph.domain.dto.PurchasePhotographDto;
import com.islack.photograph.domain.dto.TransactionDto;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.PhotographAccess;
import com.islack.photograph.repository.PhotographAccessRepository;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.service.StoreClient;
import com.islack.photograph.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private PhotographAccessRepository photographAccessRepository;

    @Autowired
    private PhotographRepository photographRepository;

    @Override
    public ResponseEntity<TransactionDto> purchasePhotograph(String bearer, String username, PurchasePhotographDto purchasePhotographDto) {
        ResponseEntity<TransactionDto> r = storeClient.purchasePhotograph(bearer, purchasePhotographDto);

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
    public ResponseEntity<String> test(String bearer, Long id) {
        return storeClient.test(bearer, id);
    }
}
