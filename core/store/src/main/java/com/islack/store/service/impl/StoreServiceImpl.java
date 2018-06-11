package com.islack.store.service.impl;

import com.islack.store.domain.dto.PayoutDto;
import com.islack.store.domain.entity.*;
import com.islack.store.repository.*;
import com.islack.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private PhotographAccessRepository photographAccessRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PayoutRepository payoutRepository;

    @Override
    public boolean enoughCreditFor(String username, Long amount) {
        Credit c = creditRepository.findByUsername(username);
        return c.getCredits() >= amount;
    }

    @Override
    public PhotographAccess purchasePhotograph(String username, PhotographAccess photographAccess) {
        Credit c = creditRepository.findByUsername(username);
        photographAccessRepository.save(photographAccess);
        c.getTransactions().add(photographAccess);
        creditRepository.save(c);
        return photographAccess;
    }

    @Override
    public Purchase purchaseOffer(String username, Long id) {
        Credit c = creditRepository.findByUsername(username);
        Purchase p = new Purchase();
        p.setOffer(offerRepository.findOne(id));
        purchaseRepository.save(p);
        c.getTransactions().add(p);
        creditRepository.save(c);
        return p;
    }

    @Override
    public Payout payout(String username, PayoutDto payoutDto) {
        Credit c = creditRepository.findByUsername(username);
        Payout p = new Payout();
        p.setAccountEmail(payoutDto.getPaypal());
        p.setAmount(payoutDto.getAmount());
        payoutRepository.save(p);
        c.getTransactions().add(p);
        creditRepository.save(c);
        return p;
    }
}
