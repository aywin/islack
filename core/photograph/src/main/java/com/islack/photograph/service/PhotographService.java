package com.islack.photograph.service;

import com.islack.photograph.domain.dto.PhotographDto;
import com.islack.photograph.domain.entity.Photograph;

import java.util.List;

public interface PhotographService {
    Photograph dtoToEntity(PhotographDto p);
    Photograph save(Photograph p);
    List<Photograph> findOwned(String username);
    List<Photograph> findPurchased(String username);
}
