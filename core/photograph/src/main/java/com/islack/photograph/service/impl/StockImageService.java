package com.islack.photograph.service.impl;

import com.islack.photograph.domain.StockPhotoHits;
import com.islack.photograph.domain.dto.StockPhoto;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.service.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

@Service
public class StockImageService {
    @Autowired
    private StockClient stockClient;

    private String[] categories = {"abstract", "animal", "building", "dark", "drink", "sky", "food", "indoor", "people", "plant", "object", "text", "transport", "other"};


    @Async
    public void stockToDB() {

        for(int j = 0; j < categories.length; j++) {
            String c = categories[j];
            try {
                FileWriter fw = new FileWriter("/home/yassine/categories/" + c + ".txt");
                BufferedWriter bw = new BufferedWriter(fw);
                for(int i = 1; i <= 10; i++) {
                    StockPhotoHits s = stockClient.getHits(c, i);
                    for(StockPhoto sp: s.getHits()) {
                        List<String> sql = sp.toSQL(j+1);
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

    @Async
    public void executeSQL() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // the sql server url
            String url = "jdbc:sqlserver://islack.database.windows.net:1433;DatabaseName=photograph";

            // get the sql server database connection
            Connection connection = DriverManager.getConnection(url,"islack", "iDBrahim1");
            Statement s1 = connection.createStatement();
            s1.execute("SET IDENTITY_INSERT photograph ON");
            s1.execute("DELETE FROM photograph");
            s1.execute("DELETE FROM tag");

            BufferedReader reader = new BufferedReader(new FileReader("/home/yassine/categories/all.sql"));
            String line;

        while ((line = reader.readLine()) != null) {
            try {
                Statement s = connection.createStatement();
                s.execute(line);
            } catch (Exception e) {
                System.out.println(line);
                System.out.println(e.getMessage());
            }

        }


            System.out.println("finished");
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
