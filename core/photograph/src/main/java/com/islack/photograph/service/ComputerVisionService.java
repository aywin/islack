package com.islack.photograph.service;

import com.islack.photograph.domain.entity.Photograph;
import org.springframework.scheduling.annotation.Async;

public interface ComputerVisionService {
    @Async
    void analyze(Photograph p);
}
