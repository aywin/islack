package com.islack.photograph.api;

import com.islack.photograph.domain.dto.*;
import com.islack.photograph.domain.dto.vision.AnalyzeRequestDto;
import com.islack.photograph.domain.dto.vision.AnalyzedPhotographDto;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.service.*;
import feign.FeignException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("photographs")
public class PhotographController {

    @Autowired
    PhotographService photographService;

    @Autowired
    PhotographRepository photographRepository;

    @Autowired
    private AzureStorageUploaderService azureStorageUploader;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ComputerVisionService computerVisionService;

    @PostMapping("create")
    public Photograph create(
            Principal principal,
            @RequestPart("file") MultipartFile file,
            @RequestParam("credit") Long credit,
            @RequestParam(value = "tags[]", required = false) String[] tags,
            @RequestParam(value = "categories[]", required = false) String[] categories) {

        PhotographDto dto = new PhotographDto();
        dto.setCategories(categories == null ? new String[]{} : categories);
        dto.setTags(tags == null ? new String[]{} : tags);
        dto.setCredit(credit);

        String newName = principal.getName() + "-" + UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        String imageUri = this.azureStorageUploader.uploadToAzureStorage(applicationContext, file, newName.toLowerCase());

        Photograph p = photographService.dtoToEntity(dto);
        p.setUsername(principal.getName());
        p.setUri("https://islack.blob.core.windows.net/images-original/" + newName);
        p.setThumbnail("https://islack.blob.core.windows.net/images-thumbnail/" + newName);
        p = photographService.save(p);

        System.out.println("start analyzing");
        computerVisionService.analyze(p);

        System.out.println("end requessssst");
        return p;
    }

    @GetMapping("list")
    public PhotographListDto list(Principal principal) {
        PhotographListDto list = new PhotographListDto();
        list.setOwned(photographService.findOwned(principal.getName()));
        list.setPurchased(photographService.findPurchased(principal.getName()));
        return list;
    }

    @PostMapping("{photoId}/purchase")
    public ResponseEntity<?> purchase(@PathVariable("photoId") Long id, Principal principal) {
        if(photographService.ownedOrPurchased(principal.getName(), id)) {
            return new ResponseEntity<>("Already owned", HttpStatus.OK);
        }
        PurchasePhotographDto p = new PurchasePhotographDto(photographService.findOne(id));
        return storeService.purchasePhotograph(principal.getName(), p);
    }

    @PostMapping("{id}/hello")
    public ResponseEntity<String> hello(@PathVariable("id") Long id) {
        return storeService.test(id);
    }


    @GetMapping("{id}/with-recommendation")
    public ResponseEntity<PhotographWithRecommendation> recommend(@PathVariable("id") Long id) {
        Photograph p = photographService.findOne(id);
        PhotographWithRecommendation photo = new PhotographWithRecommendation();
        photo.setRecommendations(photographService.getRecommendations(p));
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<Photograph>> recommendMulti(Principal principal) {
        List<Photograph> p = photographService.findOwned(principal.getName());
        p.addAll(photographService.findPurchased(principal.getName()));
        return new ResponseEntity<>(photographService.getRecommendations(p), HttpStatus.OK);
    }

}
