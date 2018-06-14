package com.islack.photograph.domain.dto.vision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ColorDto {
    private Set<String> dominantColors = new HashSet<>();

    public Set<String> getDominantColors() {
        return dominantColors;
    }

    public void setDominantColors(Set<String> dominantColors) {
        this.dominantColors = dominantColors;
    }
}
