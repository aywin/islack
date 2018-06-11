package com.islack.store.listener;

import com.islack.store.domain.dto.AccountDto;
import com.islack.store.domain.entity.Credit;
import com.islack.store.domain.entity.Offer;
import com.islack.store.domain.entity.Purchase;
import com.islack.store.domain.entity.Transaction;
import com.islack.store.repository.CreditRepository;
import com.islack.store.repository.OfferRepository;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

@EnableBinding(NewUserListener.Sink.class)
public class NewUserListener {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CreditRepository creditRepository;

    @StreamListener(Sink.INPUT)
    public void receiveAccount(AccountDto a) throws JsonProcessingException {
        System.out.println("store: " + a);

        Credit c = new Credit();
        c.setUsername(a.getUsername());

        Offer offer = offerRepository.findOne(1L);
        Purchase p = new Purchase();
        p.setOffer(offer);
        c.getTransactions().add(p);

        creditRepository.save(c);
    }

    public interface Sink {
        String INPUT = "user-input";

        @Input(INPUT)
        SubscribableChannel userSink();
    }
}
