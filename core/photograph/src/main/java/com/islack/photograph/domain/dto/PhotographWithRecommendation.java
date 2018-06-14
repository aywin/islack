package com.islack.photograph.domain.dto;

import com.islack.photograph.domain.entity.Photograph;

import java.util.ArrayList;
import java.util.List;

public class PhotographWithRecommendation {
    private Long id;
    private String uri;
    private String username;
    private Long credit;
    private List<Photograph> recommendations = new ArrayList<>();

    public List<Photograph> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Photograph> recommendations) {
        this.recommendations = recommendations;
    }

}
