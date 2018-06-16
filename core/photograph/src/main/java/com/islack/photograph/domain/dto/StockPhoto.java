package com.islack.photograph.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class StockPhoto {
    private Long id;
    private String largeImageURL;
    private String previewURL;
    private String tags;

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String[] toTags() {
        return tags.toLowerCase().split(" ");
    }

    @Override
    public String toString() {
        return "StockPhoto{" +
                "largeImageURL='" + largeImageURL + '\'' +
                ", previewURL='" + previewURL + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }

    public List<String> toSQL(String cat) {
        List<String> sql = new ArrayList<>(300000);
        sql.add("INSERT INTO photograph (id, credit, thumbnail, uri, username, created_date, updated_date) VALUES" +
                "(" + id + ", 'islack', ' " + largeImageURL + "', '" + previewURL + "', GETDATE(), GETDATE());");
        for(String t: toTags()) {
            sql.add("INSERT INTO tag(tag) VALUES ('" + t + "')");
            sql.add("INSERT INTO photograph_tags (photograph_id, tags_tag) VALUES (" + id + ", '" + t + "');");
        }

        sql.add("INSERT INTO photograph_categories (photograph_id, categories_id) VALUES (" + id + ", '" + cat + "');");

        return sql;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
