package com.islack.photograph.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhotographDto {
    private String[] tags;
    private String[] categories;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
