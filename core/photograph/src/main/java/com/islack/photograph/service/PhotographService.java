package com.islack.photograph.service;

import com.islack.photograph.domain.dto.PhotographDto;
import com.islack.photograph.domain.entity.Photograph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhotographService {
    Photograph dtoToEntity(PhotographDto p);
    Photograph save(Photograph p);
    List<Photograph> findOwned(String username);
    List<Photograph> findPurchased(String username);
    Photograph findOne(Long id);
    boolean ownedOrPurchased(String username, Long id);
    Page<Photograph> getRecommendations(Photograph p, Pageable pageable);
    Page<Photograph> getRecommendations(List<Photograph> p, Pageable pageable);
    Page<Photograph> byCategory(String slug, Pageable pageable);
    Page<Photograph> findAll(Pageable pageable);
}
