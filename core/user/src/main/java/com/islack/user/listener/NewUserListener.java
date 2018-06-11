package com.islack.user.listener;

import com.islack.user.UserApplication;
import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class NewUserListener {

    private Logger logger = LoggerFactory.getLogger(NewUserListener.class);

    @StreamListener(Sink.INPUT)
    public void receiveAccount(String a) throws JsonProcessingException {
        System.out.println(a);
        logger.error(a);
    }
}
