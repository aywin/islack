package com.islack.oauth.handler;

import com.islack.oauth.dto.AccountDto;
import com.islack.oauth.model.Account;
import com.islack.oauth.repository.AccountRepository;
import com.islack.oauth.repository.RoleRepostiory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@RepositoryEventHandler
@EnableBinding(AccountEventHandler.Source.class)
public class AccountEventHandler {

    @Autowired
    private Source source;

    @Autowired
    private RoleRepostiory roleRepostiory;

    @Autowired
    private AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(AccountEventHandler.class);

    @HandleAfterCreate(Account.class)
    public void publishUser(Account a) {
        a.getRoles().add(roleRepostiory.findByName("ROLE_USER"));
        accountRepository.save(a);
        this.source.output().send(MessageBuilder.withPayload(new AccountDto(a)).build());
    }

    public interface Source {
        String OUTPUT = "user-output";

        @Output(OUTPUT)
        MessageChannel output();
    }
}
