package com.islack.store.api;

import com.islack.store.domain.dto.PayoutDto;
import com.islack.store.domain.dto.TransactionDto;
import com.islack.store.domain.entity.PhotographAccess;
import com.islack.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("transaction")
public class PurchaseController {

    @Autowired
    private StoreService storeService;

    @PostMapping("photograph")
    public ResponseEntity<TransactionDto> purchasePhotograph(@RequestBody PhotographAccess photographAccess, Principal principal) {
        if(storeService.enoughCreditFor(principal.getName(), photographAccess.getCredit())) {
            storeService.purchasePhotograph(principal.getName(), photographAccess);
            return new ResponseEntity<>(new TransactionDto(photographAccess), HttpStatus.OK);
        }

        return new ResponseEntity<>((TransactionDto) null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("test")
    public ResponseEntity<String> test(Principal principal, @RequestBody Long id) {
        return new ResponseEntity<>(principal.getName() + ", " + id, HttpStatus.OK);
    }

    @PostMapping("offer/{id}")
    public ResponseEntity<TransactionDto> purchaseOffer(@PathVariable("id") Long offerId, Principal principal) {
        TransactionDto dto = new TransactionDto(storeService.purchaseOffer(principal.getName(), offerId));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("payout")
    public ResponseEntity<TransactionDto> payout(@RequestBody PayoutDto payoutDto, Principal principal) {
        TransactionDto dto = new TransactionDto(storeService.payout(principal.getName(), payoutDto));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
