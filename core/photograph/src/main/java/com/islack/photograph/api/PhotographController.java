package com.islack.photograph.api;

import com.islack.photograph.domain.dto.PhotographDto;
import com.islack.photograph.domain.dto.PhotographListDto;
import com.islack.photograph.domain.entity.Category;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.Tag;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.repository.TagRepository;
import com.islack.photograph.service.PhotographService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("photographs")
public class PhotographController {

    @Autowired
    PhotographService photographService;

    @PostMapping("create")
    public Photograph create(@RequestBody PhotographDto dto, Principal principal) {
        Photograph p = photographService.dtoToEntity(dto);
        p.setUsername(principal.getName());
        return photographService.save(p);
    }

    @GetMapping("list")
    public PhotographListDto list(Principal principal) {
        PhotographListDto list = new PhotographListDto();
        list.setOwned(photographService.findOwned(principal.getName()));
        list.setPurchased(photographService.findPurchased(principal.getName()));
        return list;
    }

}
