package com.islack.photograph.service.impl;

import com.islack.photograph.domain.StockPhotoHits;
import com.islack.photograph.domain.dto.StockPhoto;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.service.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

@Service
public class StockImageService {
    @Autowired
    private StockClient stockClient;

    private String[] categories = {"abstract", "animal", "building", "dark", "drink", "sky", "food", "indoor", "people", "plant", "object", "text", "transport", "other"};


    @Async
    public void stockToDB() {

        for(String c: categories) {
            try {
                FileWriter fw = new FileWriter("/home/yassine/categories/" + c + ".txt");
                BufferedWriter bw = new BufferedWriter(fw);
                for(int i = 1; i <= 10; i++) {
                    StockPhotoHits s = stockClient.getHits(c, i);
                    for(StockPhoto sp: s.getHits()) {
                        List<String> sql = sp.toSQL(c);
                        for(String query: sql) {
                            bw.write(query);
                            bw.newLine();
                        }
                    }

                }
                fw.flush();
                bw.flush();
                fw.close();
                bw.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        System.out.println("FINISH");

    }

    private Photograph hitToPhotograph(StockPhoto d) {
        Photograph p = new Photograph();
        p.setThumbnail(d.getPreviewURL());
        p.setUri(d.getLargeImageURL());
        p.setUsername("islack");
        p.setCredit(5L);
        return p;
    }


}
