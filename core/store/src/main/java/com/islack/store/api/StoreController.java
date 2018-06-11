package com.islack.store.api;

import com.islack.store.domain.dto.CreditDto;
import com.islack.store.domain.entity.Credit;
import com.islack.store.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("store")
public class StoreController {

    @Autowired
    private CreditRepository creditRepository;

    @PreAuthorize("#oauth2.hasScope('openid') and hasRole('ROLE_USER')")
    @GetMapping("credit")
    public CreditDto getCredit(Principal principal) {
        Credit c = creditRepository.findByUsername(principal.getName());
        return new CreditDto(c);
    }

}
