package com.islack.photograph.service.impl;

import com.islack.photograph.domain.StockPhotoHits;
import com.islack.photograph.domain.dto.StockPhoto;
import com.islack.photograph.domain.entity.Category;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.Tag;
import com.islack.photograph.repository.CategoryRepository;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.repository.TagRepository;
import com.islack.photograph.service.AzureStorageUploaderService;
import com.islack.photograph.service.ComputerVisionService;
import com.islack.photograph.service.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StockImageService {
    @Autowired
    private StockClient stockClient;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PhotographRepository photographRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AzureStorageUploaderService azureStorageUploader;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ComputerVisionService computerVisionService;

    private String[] categories = {"abstract", "animal", "building", "dark", "drink", "sky", "food", "indoor", "people", "plant", "object", "text", "transport", "other"};

    /*@Async
    public void stockToDB2() {
        List<Photograph> photographs = new ArrayList<>();

        String[] cs = {"abstract", "animal"};
        for(String c : cs) {
            for(int i = 1; i < 2; i++) {
                try {
                    System.out.println(c + "   hellelele");
                    StockPhotoHits s = stockClient.getHits(c, i);
                    System.out.println("SIIZE == " + s.getHits().size());
                    for(StockPhoto d: s.getHits()) {
                        Photograph p = new Photograph();
                        p.setThumbnail(d.getPreviewURL());
                        p.setUri(d.getLargeImageURL());
                        p.setTags(tagRepository.findOrCreate(d.toTags()));
                        p.setUsername("islack");
                        p.setCredit(5L);
                        Category ct = categoryRepository.findBySlug(c);
                        p.getCategories().add(ct);
                        photographRepository.save(p);
                        photographs.add(p);
                        photographRepository.flush();

                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }

                System.out.println("fin helelele ==" + photographs.size());
            }

        }

        System.out.println("finfinfinfinfinfin");
    }*/

    @Transactional
    @Async
    public void stockToDB(int category, int i) {
        List<Photograph> photographs = new ArrayList<>();

        String c = categories[category];
        Category ct = categoryRepository.findBySlug(c);
        try {
            StockPhotoHits s = stockClient.getHits(c, i);
            System.out.println("SIIZE == " + s.getHits().size());
            for (StockPhoto d : s.getHits()) {
                String fileName = "islack-" + UUID.randomUUID() + ".jpg";
                azureStorageUploader.uploadFromStockToAzure(applicationContext, d, fileName);
                Photograph p = new Photograph();
                p.setThumbnail("https://islack.blob.core.windows.net/images-thumbnail/" + fileName);
                p.setUri("https://islack.blob.core.windows.net/images-original/" + fileName);
                List<Tag> tags = tagRepository.findOrCreate(d.toTags());
                p.setTags(tags);
                p.setUsername("islack");
                p.setCredit(5L);
                p.getCategories().add(ct);
                computerVisionService.analyzeWithoutSaving(p);
                photographs.add(p);
            }
            System.out.println("fin helelele ==" + photographs.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for(Photograph ph: photographs) {
            photographRepository.save(ph);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Photograph hitToPhotograph(StockPhoto d) {
        Photograph p = new Photograph();
        p.setThumbnail(d.getPreviewURL());
        p.setUri(d.getLargeImageURL());
        p.setTags(tagRepository.findOrCreate(d.toTags()));
        p.setUsername("islack");
        p.setCredit(5L);
        return p;
    }


}
