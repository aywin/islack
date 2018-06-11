package com.islack.store.service;

import com.islack.store.domain.dto.PayoutDto;
import com.islack.store.domain.entity.Payout;
import com.islack.store.domain.entity.PhotographAccess;
import com.islack.store.domain.entity.Purchase;

public interface StoreService {
    boolean enoughCreditFor(String username, Long amount);
    PhotographAccess purchasePhotograph(String username, PhotographAccess photographAccess);
    Purchase purchaseOffer(String username, Long id);
    Payout payout(String username, PayoutDto payoutDto);
}
