package com.islack.photograph.domain.dto;

import com.islack.photograph.domain.entity.Photograph;

import java.util.List;

public class PhotographDto {
    private String uri;
    private List<String> tags;
    private List<String> categories;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
