package com.islack.photograph.service.impl;

import com.islack.photograph.domain.dto.vision.AnalyzeRequestDto;
import com.islack.photograph.domain.dto.vision.AnalyzedPhotographDto;
import com.islack.photograph.domain.entity.Category;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.Tag;
import com.islack.photograph.repository.CategoryRepository;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.repository.TagRepository;
import com.islack.photograph.service.ComputerVisionClient;
import com.islack.photograph.service.ComputerVisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputerVisionServiceImpl implements ComputerVisionService {
    @Autowired
    private ComputerVisionClient computerVisionClient;

    @Autowired
    private PhotographRepository photographRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;


    private static String key = "bd3a80593d4d47f3b0757166f764161b";

    private void submitAnalyzed(Photograph p, AnalyzedPhotographDto a) {
        List<Tag> tags = tagRepository.findOrCreate(a.toTags());
        p.getTags().addAll(tags);
        Category c = categoryRepository.findBySlug(a.toCategory());
        p.getCategories().add(c);
        photographRepository.save(p);
    }

    @Override
    @Async
    public void analyze(Photograph p) {
        AnalyzeRequestDto request = new AnalyzeRequestDto();
        request.setUrl(p.getUri());
        submitAnalyzed(p, computerVisionClient.analyze(key, request));
    }

    @Override
    public void analyzeWithoutSaving(Photograph p) {
        AnalyzeRequestDto request = new AnalyzeRequestDto();
        request.setUrl(p.getUri());
        attachAnalyzed(p, computerVisionClient.analyze(key, request));
    }

    private void attachAnalyzed(Photograph p, AnalyzedPhotographDto a) {
        List<Tag> tags = tagRepository.findOrCreate(a.toTags());
        p.getTags().addAll(tags);
        Category c = categoryRepository.findBySlug(a.toCategory());
        p.getCategories().add(c);
    }
}
