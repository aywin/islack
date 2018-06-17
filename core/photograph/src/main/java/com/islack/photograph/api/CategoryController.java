package com.islack.photograph.api;

import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.service.PhotographService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private PhotographService photographService;

    @GetMapping("{category}")
    public ResponseEntity<Page<Photograph>> byCategory(@PathVariable("category") String slug) {
        Pageable pageable = new PageRequest(0, 20);
        return new ResponseEntity<>(photographService.byCategory(slug, pageable), HttpStatus.OK);
    }
}
