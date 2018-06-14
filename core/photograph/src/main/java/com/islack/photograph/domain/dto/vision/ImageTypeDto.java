package com.islack.photograph.domain.dto.vision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ImageTypeDto {
    private int clipArtType;
    private int lineDrawingType;

    public int getClipArtType() {
        return clipArtType;
    }

    public void setClipArtType(int clipArtType) {
        this.clipArtType = clipArtType;
    }

    public int getLineDrawingType() {
        return lineDrawingType;
    }

    public void setLineDrawingType(int lineDrawingType) {
        this.lineDrawingType = lineDrawingType;
    }
}
