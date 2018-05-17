package com.islack.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ClientConfiguration {

    @Value("${my.property.test}")
    private String property;

    public String showProperties() {
        return String.format("Hello from %s", property);
    }

}