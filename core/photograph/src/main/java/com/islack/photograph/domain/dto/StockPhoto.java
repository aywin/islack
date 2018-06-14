package com.islack.photograph.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class StockPhoto {
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
}
