package com.islack.photograph.domain.dto.vision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalyzedPhotographDto {
    private List<CategoryDto> categories = new ArrayList<>();
    private ColorDto color;
    private DescriptionDto description;
    private ImageTypeDto imageType;

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public ColorDto getColor() {
        return color;
    }

    public void setColor(ColorDto color) {
        this.color = color;
    }

    public DescriptionDto getDescription() {
        return description;
    }

    public void setDescription(DescriptionDto description) {
        this.description = description;
    }

    public ImageTypeDto getImageType() {
        return imageType;
    }

    public void setImageType(ImageTypeDto imageType) {
        this.imageType = imageType;
    }

    public List<String> toTags() {
        List<String> tags = new ArrayList<>();
        tags.addAll(description.getTags());
        tags.addAll(color.getDominantColors());
        return tags;
    }

    public String toCategory() {
        CategoryDto c = categories.get(0);
        for(CategoryDto dto: categories) {
            if(dto.getScore() > c.getScore()) {
                c = dto;
            }
        }
        return c.getName().split("_")[0];
    }
}
