package com.islack.photograph.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return new AuthForwardInterceptor();
    }
}
