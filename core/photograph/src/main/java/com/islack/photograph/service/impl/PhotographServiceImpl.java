package com.islack.photograph.service.impl;

import com.islack.photograph.domain.dto.PhotographDto;
import com.islack.photograph.domain.entity.Category;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.PhotographAccess;
import com.islack.photograph.repository.CategoryRepository;
import com.islack.photograph.repository.PhotographAccessRepository;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.repository.TagRepository;
import com.islack.photograph.service.PhotographService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PhotographServiceImpl implements PhotographService {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PhotographRepository photographRepository;
    @Autowired
    PhotographAccessRepository photographAccessRepository;

    @Override
    public Photograph dtoToEntity(PhotographDto dto) {
        Photograph p = new Photograph();
        p.setTags(tagRepository.findOrCreate(dto.getTags()));
        p.setCategories(categoryRepository.findAllByTitleIn(dto.getCategories()));
        p.setCredit(dto.getCredit());

        return p;
    }

    @Override
    public Photograph save(Photograph p) {
        return photographRepository.save(p);
    }

    @Override
    public List<Photograph> findOwned(String username) {
        return photographRepository.findByUsername(username);
    }

    @Override
    public List<Photograph> findPurchased(String username) {
        return photographAccessRepository
                .findByUsername(username)
                .stream()
                .map(PhotographAccess::getPhotograph)
                .collect(Collectors.toList());
    }

    @Override
    public Photograph findOne(Long id) {
        return photographRepository.findOne(id);
    }

    @Override
    public boolean ownedOrPurchased(String username, Long id) {
        if(photographRepository.findByUsernameAndId(username, id).isPresent()) {
            return true;
        }
        Photograph p = photographRepository.findOne(id);
        return photographAccessRepository.findByUsernameAndPhotograph(username, p).isPresent();
    }

    @Override
    public Page<Photograph> getRecommendations(Photograph p, Pageable pageable) {
        return photographRepository.findRecommended(p.getId(), pageable);
    }

    @Override
    public Page<Photograph> getRecommendations(List<Photograph> p, Pageable pageable) {
        return p.size() > 0 ? photographRepository.findRecommended(p.stream().map(Photograph::getId).collect(Collectors.toList()), pageable) :
                photographRepository.findPopular(pageable);
    }

    @Override
    public Page<Photograph> byCategory(String slug, Pageable pageable) {
        return photographRepository.findByCategoriesSlug(slug, pageable);
    }

    @Override
    public Page<Photograph> findAll(Pageable pageable) {
        return photographRepository.findAll(pageable);
    }
}
