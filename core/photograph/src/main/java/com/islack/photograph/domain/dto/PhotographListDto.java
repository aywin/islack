package com.islack.photograph.domain.dto;

import com.islack.photograph.domain.entity.Photograph;

import java.util.List;

public class PhotographListDto {
    List<Photograph> owned;
    List<Photograph> purchased;

    public List<Photograph> getOwned() {
        return owned;
    }

    public void setOwned(List<Photograph> owned) {
        this.owned = owned;
    }

    public List<Photograph> getPurchased() {
        return purchased;
    }

    public void setPurchased(List<Photograph> purchased) {
        this.purchased = purchased;
    }
}
