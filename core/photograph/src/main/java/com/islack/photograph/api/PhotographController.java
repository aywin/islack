package com.islack.photograph.api;

import com.islack.photograph.domain.dto.PhotographDto;
import com.islack.photograph.domain.dto.PhotographListDto;
import com.islack.photograph.domain.entity.Category;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.Tag;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.repository.TagRepository;
import com.islack.photograph.service.AzureStorageUploaderService;
import com.islack.photograph.service.PhotographService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("photographs")
public class PhotographController {

    @Autowired
    PhotographService photographService;

    @Autowired
    private AzureStorageUploaderService azureStorageUploader;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("create")
    public Photograph create(
            @RequestPart("file") MultipartFile file,
            @RequestParam("tags[]") String[] tags,
            @RequestParam("categories[]") String[] categories,
            Principal principal) {

        PhotographDto dto = new PhotographDto();
        dto.setCategories(categories);
        dto.setTags(tags);

        String newName = principal.getName() + "-" + UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        String imageUri = this.azureStorageUploader.uploadToAzureStorage(applicationContext, file, newName.toLowerCase());

        Photograph p = photographService.dtoToEntity(dto);
        //Photograph p = new Photograph();
        p.setUsername(principal.getName());
        p.setUri(newName);
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
