package com.islack.photograph.domain.dto;

import com.islack.photograph.domain.entity.Photograph;

import java.util.ArrayList;
import java.util.List;

public class PhotographWithRecommendation {
    private Long id;
    private String uri;
    private String username;
    private String thumbnail;
    private Long credit;
    private List<Photograph> recommendations = new ArrayList<>();

    public PhotographWithRecommendation(Photograph p) {
        id = p.getId();
        uri = p.getUri();
        username = p.getUsername();
        thumbnail = p.getThumbnail();
        credit = p.getCredit();
    }

    public List<Photograph> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Photograph> recommendations) {
        this.recommendations = recommendations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }
}
