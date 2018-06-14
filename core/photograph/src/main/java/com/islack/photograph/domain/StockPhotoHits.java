package com.islack.photograph.domain;

import com.islack.photograph.domain.dto.StockPhoto;

import java.util.List;

public class StockPhotoHits {
    private int totalHits;
    private int total;
    List<StockPhoto> hits;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<StockPhoto> getHits() {
        return hits;
    }

    public void setHits(List<StockPhoto> hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "StockPhotoHits{" +
                "totalHits=" + totalHits +
                ", total=" + total +
                ", hits=" + hits +
                '}';
    }
}
