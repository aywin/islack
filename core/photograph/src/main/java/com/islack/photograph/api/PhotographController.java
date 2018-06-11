package com.islack.photograph.api;

import com.islack.photograph.domain.dto.PhotographDto;
import com.islack.photograph.domain.dto.PhotographListDto;
import com.islack.photograph.domain.dto.PurchasePhotographDto;
import com.islack.photograph.domain.dto.TransactionDto;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.service.AzureStorageUploaderService;
import com.islack.photograph.service.PhotographService;
import com.islack.photograph.service.StoreClient;
import com.islack.photograph.service.StoreService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
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

    @Autowired
    private StoreService storeService;

    @PostMapping("create")
    public Photograph create(
            @RequestPart("file") MultipartFile file,
            @RequestParam("tags[]") String[] tags,
            @RequestParam("categories[]") String[] categories,
            @RequestParam("credit") Long credit,
            Principal principal) {

        PhotographDto dto = new PhotographDto();
        dto.setCategories(categories);
        dto.setTags(tags);
        dto.setCredit(credit);

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

    @PostMapping("{photoId}/purchase")
    public ResponseEntity<?> purchase(@PathVariable("photoId") Long id, @RequestHeader("Authorization") String bearer, Principal principal) {
        if(photographService.ownedOrPurchased(principal.getName(), id)) {
            return new ResponseEntity<>("Already owned", HttpStatus.OK);
        }
        PurchasePhotographDto p = new PurchasePhotographDto(photographService.findOne(id));
        return storeService.purchasePhotograph(bearer, principal.getName(), p);
    }

    @PostMapping("{id}/hello")
    public ResponseEntity<String> hello(@PathVariable("id") Long id, @RequestHeader("Authorization") String bearer) {
        return storeService.test(bearer, id);
    }

}
