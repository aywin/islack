package com.islack.photograph.service;

import com.islack.photograph.domain.dto.PhotographDto;
import com.islack.photograph.domain.entity.Photograph;

import java.util.List;

public interface PhotographService {
    Photograph dtoToEntity(PhotographDto p);
    Photograph save(Photograph p);
    List<Photograph> findOwned(String username);
    List<Photograph> findPurchased(String username);
    Photograph findOne(Long id);
    boolean ownedOrPurchased(String username, Long id);
    List<Photograph> getRecommendations(Photograph p);
    List<Photograph> getRecommendations(List<Photograph> p);
    List<Photograph> byCategory(String slug);
}
